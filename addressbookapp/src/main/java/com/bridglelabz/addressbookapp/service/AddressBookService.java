package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.exception.ContactNotFoundException;
import com.bridglelabz.addressbookapp.model.AddressBook;
import com.bridglelabz.addressbookapp.repository.AddressBookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
public class AddressBookService implements IAddressService {

    @Autowired
    private AddressBookRepository repository;

    @Override
    public AddressBook addContact(AddressBookDTO addressBookDTO) {
        log.info("Adding Address Entry: {}", addressBookDTO);
        AddressBook contact = new AddressBook(addressBookDTO);
        return repository.save(contact);
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO addressBookDTO) {
        log.info("Updating address with id: {}", id);
        AddressBook contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));

        contact.setName(addressBookDTO.getName());
        contact.setAddress(addressBookDTO.getAddress());
        contact.setPhoneNumber(addressBookDTO.getPhoneNumber());
        contact.setEmail(addressBookDTO.getEmail());

        return repository.save(contact);
    }

    @Override
    public AddressBook getContactById(int id) {
        log.info("Fetching address with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));
    }

    @Override
    public List<AddressBook> getAllContacts() {
        log.info("Fetching all address entries");
        return repository.findAll();
    }

    @Override
    public void deleteContact(int id) {
        log.info("Deleting address with id: {}", id);
        AddressBook contact = repository.findById(id)
                .orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));
        repository.delete(contact);
    }
}