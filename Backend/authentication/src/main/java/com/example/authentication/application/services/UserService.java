package com.example.authentication.application.services;

import com.example.authentication.api.dto.ResponseTokenDTO;
import com.example.authentication.api.dto.UserDTO;
import com.example.authentication.domain.models.User;
import com.example.authentication.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationTokenService authenticationTokenService;

    public ResponseEntity<Object> getUsers(){
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO){
        User user = new User();
        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO();
        if (userRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.badRequest().body("the email is already exist");
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User saved = userRepository.save(user);
        responseTokenDTO.setToken(authenticationTokenService.getTokenWithoutClaims(user));
        Map<String, Object> response = new HashMap<>();
        response.put("User saved: " , saved);
        response.put("token: ", responseTokenDTO.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
