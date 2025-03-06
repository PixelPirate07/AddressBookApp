package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressBookService implements IAddressService {

    private final List<AddressBook> contactList = new ArrayList<>();
    private int contactIdCounter = 1;

    @Override
    public AddressBook addContact(AddressBookDTO addressBookDTO) {
        AddressBook newContact = new AddressBook(contactIdCounter++, addressBookDTO);
        contactList.add(newContact);
        return newContact;
    }

    @Override
    public List<AddressBook> getAllContacts() {
        return contactList;
    }

    @Override
    public AddressBook getContactById(int id) {
        return contactList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO addressBookDTO) {
        AddressBook existingContact = getContactById(id);
        if (existingContact != null) {
            existingContact.update(addressBookDTO);
        }
        return existingContact;
    }

    @Override
    public void deleteContact(int id) {
        contactList.removeIf(contact -> contact.getId() == id);
    }
}