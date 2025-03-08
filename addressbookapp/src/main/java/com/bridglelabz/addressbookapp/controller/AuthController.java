package com.bridglelabz.addressbookapp.controller;

import com.bridglelabz.addressbookapp.dto.LoginDTO;
import com.bridglelabz.addressbookapp.dto.UserDTO;
import com.bridglelabz.addressbookapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody UserDTO userDTO) {
        authService.registerUser(userDTO);
        return "User Registered Successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return authService.loginUser(loginDTO.getEmail(), loginDTO.getPassword());
    }
}
