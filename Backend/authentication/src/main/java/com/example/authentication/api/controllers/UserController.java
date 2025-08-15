package com.example.authentication.api.controllers;

import com.example.authentication.api.dto.UserDTO;
import com.example.authentication.application.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers(){
        return userService.getUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO userDTO){
        return userService.createUser(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted sucessfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDTO userDTO){
        return userService.updateUser(userDTO);
    }
}
