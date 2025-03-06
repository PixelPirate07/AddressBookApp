package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;
import java.util.List;

public interface IAddressService {
    AddressBook addContact(AddressBookDTO addressBookDTO);
    List<AddressBook> getAllContacts();
    AddressBook getContactById(int id);
    AddressBook updateContact(int id, AddressBookDTO addressBookDTO);
    void deleteContact(int id);
}