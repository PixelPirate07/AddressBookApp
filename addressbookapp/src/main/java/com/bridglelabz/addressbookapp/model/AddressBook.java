package com.bridglelabz.addressbookapp.model;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import jakarta.persistence.*;
@Entity
@Table(name = "addressbook")
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String phonenumber;

    public Long getId() {
        return id;
    }

    private String email;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    AddressBook(AddressBookDTO addressBookDTO){
        this.name=addressBookDTO.getName();
        this.address=addressBookDTO.getAddress();
        this.phonenumber=addressBookDTO.getPhonenumber();
        this.email=addressBookDTO.getEmail();
    }



    public void setName(String name) {
        this.name = name;
    }
}
