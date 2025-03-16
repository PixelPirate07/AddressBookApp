package com.bridglelabz.addressbookapp.service;

import com.bridglelabz.addressbookapp.dto.ResetPasswordDTO;
import com.bridglelabz.addressbookapp.model.User;
import com.bridglelabz.addressbookapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void generateResetToken(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String token = UUID.randomUUID().toString();
            user.setResetToken(token);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(30));
            userRepository.save(user);

            // Send email with reset link
            String resetLink = "http://localhost:8080/auth/reset?token=" + token;
            emailService.sendEmail(user.getEmail(), "Password Reset Request",
                    "Click the link to reset your password: " + resetLink);
        } else {
            throw new RuntimeException("User with email " + email + " not found.");
        }
    }

    public void resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
        Optional<User> userOptional = userRepository.findByResetToken(token);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getTokenExpiry().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Token expired. Please request a new reset link.");
            }

            // Reset Password
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            user.setResetToken(null);
            user.setTokenExpiry(null);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Invalid reset token.");
        }
    }
}
