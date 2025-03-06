package com.bridglelabz.addressbookapp.controller;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;
import com.bridglelabz.addressbookapp.service.IAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private IAddressService addressBookService;

    @PostMapping("/add")
    public ResponseEntity<AddressBook> addContact(@Valid @RequestBody AddressBookDTO addressBookDTO) {
        return ResponseEntity.ok(addressBookService.addContact(addressBookDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getContactById(@PathVariable int id) {
        return ResponseEntity.ok(addressBookService.getContactById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AddressBook>> getAllContacts() {
        return ResponseEntity.ok(addressBookService.getAllContacts());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AddressBook> updateContact(@PathVariable int id, @Valid @RequestBody AddressBookDTO addressBookDTO) {
        return ResponseEntity.ok(addressBookService.updateContact(id, addressBookDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        addressBookService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}