package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.UserDTO;
import com.bridglelabz.addressbookapp.model.User;
import com.bridglelabz.addressbookapp.rabbitmq.AddressBookProducer;
import com.bridglelabz.addressbookapp.repository.UserRepository;
import com.bridglelabz.addressbookapp.util.JWTUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JWTUtility jwtUtility;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private AddressBookProducer addressBookProducer;


    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User("John Doe", "john.doe@example.com", "encodedPassword", "USER");
    }

    //assertTrue
    @Test
    void testRegisterUser() {
        UserDTO userDTO = new UserDTO("Test User", "test@gmail.com", "password123", "USER");
        User testUser = new User(userDTO.getName(), userDTO.getEmail(), "encodedPassword", userDTO.getRole());

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User registeredUser = authService.registerUser(userDTO);

        assertEquals("test@gmail.com", registeredUser.getEmail());
        assertEquals("Test User", registeredUser.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }


    //assertFalse
    @Test
    void testLoginUser_InvalidPassword() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongPassword", testUser.getPassword())).thenReturn(false);

        assertFalse(passwordEncoder.matches("wrongPassword", testUser.getPassword()));
        assertThrows(RuntimeException.class,
                () -> authService.loginUser("john.doe@example.com", "wrongPassword"));
    }

    //assertThrows
    @Test
    void testLoginUser_InvalidToken() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("encodedPassword", testUser.getPassword())).thenReturn(true);
        when(jwtUtility.generateToken(anyString(), anyString())).thenThrow(new RuntimeException("Invalid Token!"));

        Exception exception = assertThrows(RuntimeException.class,
                () -> authService.loginUser("john.doe@example.com", "encodedPassword"));

        assertEquals("Invalid Token!", exception.getMessage());
    }

}
