package com.security.TryingToMakeCorrectRealizationOfJWT.controller;

import com.security.TryingToMakeCorrectRealizationOfJWT.dto.UserDTO;
import com.security.TryingToMakeCorrectRealizationOfJWT.model.User;
import com.security.TryingToMakeCorrectRealizationOfJWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/debug")
@RequiredArgsConstructor
public class DebugController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/check-password")
    public String checkPassword(@RequestBody UserDTO loginCred) {
        User user = userRepository.findByEmail(loginCred.getEmail());

        if (user == null) {
            return "User not found";
        }

        boolean matches = passwordEncoder.matches(
                loginCred.getPassword(),
                user.getPassword()
        );

        return "User: " + user.getEmail() + " Stored hash: " + user.getPassword() + " Password matches: " + matches;
    }
}