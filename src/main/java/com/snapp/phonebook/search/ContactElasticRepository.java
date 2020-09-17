package com.snapp.phonebook.search;

import com.snapp.phonebook.entity.Contact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface ContactElasticRepository extends ElasticsearchRepository<Contact, Long> {
//    Optional<Contact> findByName(@Param("name") String name);
    Contact findByName(@Param("name") String name);
}
