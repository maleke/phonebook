package com.snapp.phonebook.mapper;

import com.snapp.phonebook.dto.ContactDto;
import com.snapp.phonebook.entity.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContactMapper {
    ContactDto contactToContactDto(Contact contact);
    Contact contactDtoToContact(ContactDto contactDto);
}
