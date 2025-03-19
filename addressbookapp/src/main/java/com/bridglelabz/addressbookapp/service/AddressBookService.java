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
        try{
            log.info("Adding Address Entry: {}", addressBookDTO);
            AddressBook contact = new AddressBook(addressBookDTO);
            return repository.save(contact);
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO addressBookDTO) {
        try{
            log.info("Updating address with id: {}", id);
            AddressBook contact = repository.findById(id)
                    .orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));

            contact.setName(addressBookDTO.getName());
            contact.setAddress(addressBookDTO.getAddress());
            contact.setPhoneNumber(addressBookDTO.getPhoneNumber());
            contact.setEmail(addressBookDTO.getEmail());

            return repository.save(contact);
        }catch(ContactNotFoundException e){
            throw e;
        }catch (Exception e) {
            throw new RuntimeException("Failed to update contact: " + e.getMessage());
        }

    }

    @Override
    public AddressBook getContactById(int id) {
        try{
            log.info("Fetching address with id: {}", id);
            return repository.findById(id).orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));
        }catch (ContactNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch contact by ID: " + e.getMessage());
        }


    }

    @Override
    public List<AddressBook> getAllContacts() {
        try{
            log.info("Fetching all address entries");
            return repository.findAll();
        }catch (Exception e) {
            throw new RuntimeException("Failed to fetch all contacts: " + e.getMessage());
        }

    }

    @Override
    public void deleteContact(int id) {
        try{
            log.info("Deleting address with id: {}", id);
            AddressBook contact = repository.findById(id)
                    .orElseThrow(() -> new ContactNotFoundException("Contact not found for ID: " + id));
            repository.delete(contact);
        }catch (ContactNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete contact: " + e.getMessage());
        }

    }
}