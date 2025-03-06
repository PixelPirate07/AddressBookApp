package com.bridglelabz.addressbookapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AddressBookDTO {
    @NotBlank(message="name cannot be empty")
    private String name;
    @NotBlank(message="address cannot be empty")
    private String address;
    @NotBlank(message = "phone number cannot be empty")
    @Pattern(regexp = "\\d{10}")
    private String phonenumber;
    @NotBlank(message = "email cannot be empty")
    private String email;

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

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
