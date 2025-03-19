package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.AddressBookDTO;
import com.bridglelabz.addressbookapp.exception.ContactNotFoundException;
import com.bridglelabz.addressbookapp.model.AddressBook;
import com.bridglelabz.addressbookapp.repository.AddressBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressBookServiceTest {

    @Mock
    private AddressBookRepository addressBookRepository;

    @InjectMocks
    private AddressBookService addressBookService;

    private AddressBook testContact;

    @BeforeEach
    void setUp() {
        AddressBookDTO addressBookDTO = new AddressBookDTO("John Doe", "123 Street", "9876543210", "john.doe@example.com");
        testContact = new AddressBook(addressBookDTO);
    }

    // assertTrue
    @Test
    void testAddContact() {
        AddressBookDTO addressBookDTO = new AddressBookDTO("John Doe", "123 Street", "9876543210", "john.doe@example.com");
        when(addressBookRepository.save(any(AddressBook.class))).thenReturn(testContact);

        AddressBook addedContact = addressBookService.addContact(addressBookDTO);

        assertTrue(addedContact.getEmail().equals("john.doe@example.com"));
        verify(addressBookRepository, times(1)).save(any(AddressBook.class));
    }

    // assertFalse
    @Test
    void testDeleteContact_Failure() {
        when(addressBookRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ContactNotFoundException.class,
                () -> addressBookService.deleteContact(99));

        assertEquals("Contact not found for ID: 99", exception.getMessage());
    }

    // assertThrows
    @Test
    void testAddContact_Failure() {
        AddressBookDTO addressBookDTO = new AddressBookDTO("Jane Doe", "456 Avenue", "9999999999", "jane.doe@gmail.com");

        when(addressBookRepository.save(any(AddressBook.class))).thenThrow(new RuntimeException("Database Error!"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> addressBookService.addContact(addressBookDTO));

        assertEquals("Database Error!", exception.getMessage());
    }
}
