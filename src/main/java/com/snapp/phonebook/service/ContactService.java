package com.snapp.phonebook.service;

import com.snapp.phonebook.dto.ContactDto;
import com.snapp.phonebook.dto.ContactSearchDto;
import com.snapp.phonebook.entity.Contact;
import com.snapp.phonebook.entity.GithubRepository;
import com.snapp.phonebook.exceptions.ServiceException;
import com.snapp.phonebook.exceptions.error.ErrorCode;
import com.snapp.phonebook.exceptions.error.FieldErrorDTO;
import com.snapp.phonebook.mapper.ContactMapper;
import com.snapp.phonebook.repository.ContactRepository;
import com.snapp.phonebook.search.ContactElasticRepository;
import com.snapp.phonebook.utility.SerializeUtility;
import io.netty.util.internal.StringUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class ContactService {

    public static final String KEY = "githubRepository.add.contact";
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ContactMapper contactMapper;
    private final ContactElasticRepository contactElasticRepository;
    private final ContactRepository contactRepository;
    private final GithubService githubService;
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topic;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ContactService(ContactMapper contactMapper, ContactRepository contactRepository, ContactElasticRepository contactElasticRepository, GithubService githubService, RabbitTemplate rabbitTemplate, TopicExchange topic, ElasticsearchOperations elasticsearchOperations, ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.contactMapper = contactMapper;
        this.contactElasticRepository = contactElasticRepository;
        this.contactRepository = contactRepository;
        this.githubService = githubService;
        this.rabbitTemplate = rabbitTemplate;
        this.topic = topic;
        this.elasticsearchOperations = elasticsearchOperations;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Transactional
    public ContactDto save(ContactDto contactDto) throws ServiceException {
        logger.info(" Request to save Contact base on {}", contactDto);
        checkIfUserExist(contactDto.getName());
        Contact contact = contactMapper.contactDtoToContact(contactDto);
        contact = contactRepository.save(contact);
        //using queue to retrieve github repository of contact to increase performance
        rabbitTemplate.convertAndSend(topic.getName(), KEY, contact);
        return contactMapper.contactToContactDto(contact);
    }

    @RabbitListener(queues = "#{queue.name}")
    public void receive(Message contactByte) throws IOException, ClassNotFoundException, ServiceException {
        Contact contact = (Contact) SerializeUtility.deserialize(contactByte.getBody());
        String contactName = contact.getName();
        try {
            List<GithubRepository> contactGithubRepository = githubService.getContactGithubRepository(contactName).get();
            setParent(contactGithubRepository, contact);
            contact = contactRepository.save(contact);
            contactElasticRepository.save(contact);
        } catch (InterruptedException | ExecutionException e) {
            logger.error("An error occurred in calling githubRepository web service" + e.getMessage());
        }
    }

    private void checkIfUserExist(String name) throws ServiceException {
        Optional<Contact> contact = contactRepository.findByName(name);
        if (contact.isPresent())
            throw new ServiceException(new FieldErrorDTO().setErrorDescription("This name is already exist")
                    .setErrorCode(String.valueOf(ErrorCode.DUPLICATE_DATA.getCode())));
    }

    private void setParent(List<GithubRepository> contactGithubRepositories, Contact contact) {
        for (GithubRepository githubRepository : contactGithubRepositories)
            githubRepository.setContact(contact);
        contact.setGithubRepositories(contactGithubRepositories);
    }

    public List<ContactSearchDto> findContact(ContactSearchDto contactSearchDto) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        mustQuery("name", contactSearchDto.getName(), boolQueryBuilder);
        mustQuery("phone_number", contactSearchDto.getPhoneNumber(), boolQueryBuilder);
        mustQuery("organization", contactSearchDto.getOrganization(), boolQueryBuilder);
        mustQuery("github", contactSearchDto.getGithub(), boolQueryBuilder);
        IndexCoordinates index = IndexCoordinates.of("contact");
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();

        SearchHits<Contact> result = elasticsearchOperations.search(searchQuery, Contact.class, index);
        List<ContactSearchDto> contactSearchDtos = result
                .stream()
                .map(contactSearchHit ->
                        contactMapper.contactToContactSearchDto(contactSearchHit.getContent())
                ).collect(Collectors.toList());
//        for (SearchHit<Contact> contact: result) {
//            Contact c = contact.getContent();
//            System.out.println(c);
//        }


        return contactSearchDtos;
    }

    private void mustQuery(String name, String value, BoolQueryBuilder boolQueryBuilder) {
        if (StringUtils.hasLength(value)) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(name, value));
        }
    }
}
