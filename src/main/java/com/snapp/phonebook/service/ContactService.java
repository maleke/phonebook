package com.snapp.phonebook.service;

import com.snapp.phonebook.dto.ContactDto;
import com.snapp.phonebook.entity.Contact;
import com.snapp.phonebook.entity.GithubRepository;
import com.snapp.phonebook.exceptions.ServiceException;
import com.snapp.phonebook.mapper.ContactMapper;
import com.snapp.phonebook.repository.ContactRepository;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;
    private final GithubService githubService;

    public ContactService(ContactMapper contactMapper, ContactRepository contactRepository, GithubService githubService) {
        this.contactMapper = contactMapper;
        this.contactRepository = contactRepository;
        this.githubService = githubService;
    }

    @Transactional
    public ContactDto save(ContactDto contactDto) throws ServiceException {
        logger.info(" Request to save Contact base on {}", contactDto);
        Contact contact = contactMapper.contactDtoToContact(contactDto);
        List<GithubRepository> contactGithubRepository = githubService.getContactGithubRepository(contact.getName());
        contact = setParent(contactGithubRepository, contact);
        contact = contactRepository.save(contact);
        return contactMapper.contactToContactDto(contact);
    }

    private Contact setParent(List<GithubRepository> contactGithubRepositorys, Contact contact) {
        for (GithubRepository githubRepository : contactGithubRepositorys)
            githubRepository.setContact(contact);
        contact.setGithubRepositories(contactGithubRepositorys);
        return contact;
    }

}
