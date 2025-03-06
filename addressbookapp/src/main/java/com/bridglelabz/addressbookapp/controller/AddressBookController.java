package com.bridglelabz.addressbookapp.controller;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;
import com.bridglelabz.addressbookapp.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private IAddressService addressBookService;

    @PostMapping("/add")
    public AddressBook addContact(@RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.addContact(addressBookDTO);
    }

    @GetMapping("/all")
    public List<AddressBook> getAllContacts() {
        return addressBookService.getAllContacts();
    }

    @GetMapping("/{id}")
    public AddressBook getContactById(@PathVariable int id) {
        return addressBookService.getContactById(id);
    }

    @PutMapping("/update/{id}")
    public AddressBook updateContact(@PathVariable int id, @RequestBody AddressBookDTO addressBookDTO) {
        return addressBookService.updateContact(id, addressBookDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteContact(@PathVariable int id) {
        addressBookService.deleteContact(id);
        return "Contact deleted successfully!";
    }
}