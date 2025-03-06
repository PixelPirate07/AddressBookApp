package com.bridglelabz.addressbookapp.model;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressBook {
    private int id;
    private String name;
    private String address;
    private String phonenumber;
    private String email;

    // Constructor using DTO
    public AddressBook(int id, AddressBookDTO addressBookDTO) {
        this.id = id;
        this.name = addressBookDTO.getName();
        this.address = addressBookDTO.getAddress();
        this.phonenumber = addressBookDTO.getPhonenumber();
        this.email = addressBookDTO.getEmail();
    }

    // Method to update contact details using DTO
    public void update(AddressBookDTO addressBookDTO) {
        this.name = addressBookDTO.getName();
        this.address = addressBookDTO.getAddress();
        this.phonenumber = addressBookDTO.getPhonenumber();
        this.email = addressBookDTO.getEmail();
    }

    public int getId() {
        return id;
    }

}