package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.UserDTO;
import com.bridglelabz.addressbookapp.model.User;
import com.bridglelabz.addressbookapp.rabbitmq.AddressBookProducer;
import com.bridglelabz.addressbookapp.repository.UserRepository;
import com.bridglelabz.addressbookapp.util.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTUtility jwtUtility;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    private AddressBookProducer addressBookProducer;

    public User registerUser(UserDTO userDTO) {
        try{
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRole());
            addressBookProducer.sendEmailMessage(user.getEmail(),
                    "Welcome to AddressBook App!",
                    "Congratulations " + user.getName() + "! Your registration was successful.");
            return userRepository.save(user);
        }catch (Exception e) {
            throw new RuntimeException("Failed to register user: " + e.getMessage());
        }

    }

    public String loginUser(String email, String password) {
        try{
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found!"));

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new RuntimeException("Invalid credentials!");
            }
            String role = user.getRole();
            String token=jwtUtility.generateToken(email,role);
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
            valueOperations.set("TOKEN_" + email, token, 1, TimeUnit.HOURS);
            return token;
        }catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Login process failed: " + e.getMessage());
        }

    }
}
