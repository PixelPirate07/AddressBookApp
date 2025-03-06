package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;

import java.util.List;

public interface IAddressService {
    AddressBook addContact(AddressBookDTO addressBookDTO);
    AddressBook updateContact(int id, AddressBookDTO addressBookDTO);
    AddressBook getContactById(int id);
    List<AddressBook> getAllContacts();
    void deleteContact(int id);
}