package com.bridglelabz.addressbookapp.model;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "addressbook")
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    // Default Constructor (mandatory for Hibernate)
    public AddressBook() {}

    // Parameterized Constructor (for DTO mapping)
    public AddressBook(AddressBookDTO dto) {
        this.name = dto.getName();
        this.address = dto.getAddress();
        this.phoneNumber = dto.getPhoneNumber();
        this.email = dto.getEmail();
    }

    // Getters and Setters (manual)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}