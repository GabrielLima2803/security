package com.alibou.security.user;

import org.springframework.web.bind.annotation.RestController;

import com.alibou.security.security.CustomBasicAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        try {
            User authenticatedUser = userService.authenticate(username, password);

            return ResponseEntity.ok("Login successful for user: " + authenticatedUser.getUsername());
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
