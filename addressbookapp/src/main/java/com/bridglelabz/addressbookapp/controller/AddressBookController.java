package com.bridglelabz.addressbookapp.controller;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.model.AddressBook;
import com.bridglelabz.addressbookapp.service.IAddressService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/addressbook")
public class AddressBookController {

    @Autowired
    private IAddressService addressBookService;

    @PostMapping("/add")
    @Operation(summary = "Adds a new contact", description = "Adds a new contact")
    public ResponseEntity<AddressBook> addContact(@Valid @RequestBody AddressBookDTO addressBookDTO) {
        return ResponseEntity.ok(addressBookService.addContact(addressBookDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Gets contact by id", description = "Searches a contact by id")
    public ResponseEntity<AddressBook> getContactById(@PathVariable int id) {
        return ResponseEntity.ok(addressBookService.getContactById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Gets all contacts", description = "Gives all contacts")
    public ResponseEntity<List<AddressBook>> getAllContacts() {
        return ResponseEntity.ok(addressBookService.getAllContacts());
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a contact", description = "Update a contact")
    public ResponseEntity<AddressBook> updateContact(@PathVariable int id, @Valid @RequestBody AddressBookDTO addressBookDTO) {
        return ResponseEntity.ok(addressBookService.updateContact(id, addressBookDTO));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a contact", description = "Deletes a contact")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        addressBookService.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}