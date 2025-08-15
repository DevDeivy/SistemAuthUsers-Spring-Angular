package com.example.authentication.application.services;

import com.example.authentication.api.dto.LoginDTO;
import com.example.authentication.api.dto.ResponseTokenDTO;
import com.example.authentication.api.dto.UserDTO;
import com.example.authentication.domain.models.User;
import com.example.authentication.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final ResponseTokenService responseTokenService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> getUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<Object> createUser(@RequestBody UserDTO userDTO){
        User user = new User();
        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO();
        if (userRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.badRequest().body("the user is already exist");
        }
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User saved = userRepository.save(user);
        responseTokenDTO.setToken(responseTokenService.getTokenWithoutClaims(user));
        Map<String, Object> response = new HashMap<>();
        response.put("User saved: " , saved);
        response.put("token: ", responseTokenDTO.getToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseTokenDTO login(LoginDTO loginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()
        ));

        UserDetails user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow();

        String token = responseTokenService.getTokenWithoutClaims(user);
        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(token);

        return responseTokenDTO;
    }

    public ResponseEntity<Object> deleteUser(Long id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.badRequest().body("this id are not exist");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully: " + id);
    }

    public ResponseEntity<Object> getUserById(Long id){
        if(!userRepository.existsById(id)){
            return ResponseEntity.badRequest().body("this id are not exist");
        }
        var found  = userRepository.findById(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(found);
    }

    public ResponseEntity<Object> updateUser(@RequestBody UserDTO userDTO){
        if(!userRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found by email");
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(user);
    }
}
