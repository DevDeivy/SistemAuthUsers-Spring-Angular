package com.example.authentication.api.controllers;

import com.example.authentication.api.dto.LoginDTO;
import com.example.authentication.api.dto.UserDTO;
import com.example.authentication.application.services.AuthenticationService;
import com.example.authentication.application.services.AuthenticationTokenService;
import com.example.authentication.application.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationTokenService authenticationTokenService;
    private final AuthenticationService authenticationService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginDTO loginDTO){
        return ResponseEntity.ok(authenticationService.loginUser(loginDTO));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Object> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
        return authenticationTokenService.refreshToken(authHeader);
    }
}
