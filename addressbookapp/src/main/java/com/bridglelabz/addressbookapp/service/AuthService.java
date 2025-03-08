package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.UserDTO;
import com.bridglelabz.addressbookapp.model.User;
import com.bridglelabz.addressbookapp.repository.UserRepository;
import com.bridglelabz.addressbookapp.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtility jwtUtility;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
        return userRepository.save(user);
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials!");
        }

        return jwtUtility.generateToken(email);
    }
}
