package com.snapp.phonebook.search;

import com.snapp.phonebook.entity.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ContactElasticRepository extends ElasticsearchRepository<Contact, Long> {
}
