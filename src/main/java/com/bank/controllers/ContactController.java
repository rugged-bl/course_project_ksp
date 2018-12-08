package com.bank.controllers;

import com.bank.data.ClientRepository;
import com.bank.data.ContactRepository;
import com.bank.data.entities.Contact;
import com.bank.exceptions.ResourceNotFoundException;
import com.bank.util.ExceptionFormattingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContactController {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping(value = "/clients/{clientId}/contacts/")
    public List<Contact> getAllContactsByClientId(@PathVariable(value = "clientId") Long clientId) {
        return contactRepository.findByClientId(clientId);
    }

    @PostMapping(value = "/clients/{clientId}/contacts/")
    public Contact createContact(@PathVariable(value = "clientId") Long clientId,
                                 @Valid @RequestBody Contact contact) {
        clientRepository.findById(clientId).map(client -> {
            contact.setClient(client);
            return contactRepository.save(contact);
        }).orElseThrow(() -> ExceptionFormattingUtil.accountNotFoundException(clientId));

        return contact;
    }

    @GetMapping(value = "/contacts/")
    public Iterable<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @PutMapping("/contacts/{contactId}")
    public Contact updateContact(@PathVariable(value = "contactId") Long contactId,
                                 @Valid @RequestBody Contact contactRequest) {
        return contactRepository.findById(contactId).map(contact -> {
            contact.setAddress(contactRequest.getAddress());
            return contactRepository.save(contact);
        }).orElseThrow(() -> new ResourceNotFoundException("ContactId " + contactId + "not found"));
    }

    @DeleteMapping("/contacts/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable(value = "contactId") Long contactId) {
        return contactRepository.findById(contactId).map(contact -> {
            contactRepository.delete(contact);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("ContactId " + contactId + " not found"));
    }
}
