package com.snapp.phonebook.web.rest;

import com.snapp.phonebook.dto.ContactDto;
import com.snapp.phonebook.dto.ContactSearchDto;
import com.snapp.phonebook.exceptions.ServiceException;
import com.snapp.phonebook.service.ContactService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping
public class ContactController {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public ResponseEntity<ContactDto> createContact(@Valid @RequestBody ContactDto contactDto)
            throws ServiceException {
        logger.debug("REST request to save contact : {}", contactDto);
        ContactDto result = contactService.save(contactDto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/contacts/search", method = RequestMethod.POST)
    public ResponseEntity<List<ContactSearchDto>> findContact(@Valid @RequestBody ContactSearchDto contactSearchDto)
            throws ServiceException {

        logger.debug("REST request to find contact : {}", contactSearchDto);
        List<ContactSearchDto> result = contactService.findContact(contactSearchDto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
