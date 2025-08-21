package com.example.authentication.application.services;

import com.example.authentication.api.dto.LoginDTO;
import com.example.authentication.api.dto.ResponseTokenDTO;
import com.example.authentication.infraestructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationTokenService authenticationTokenService;

    public ResponseEntity<Object> loginUser(LoginDTO loginDTO){
        if(!userRepository.existsByEmail(loginDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("please enter an email");
        }
        login(loginDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("login successfully");
    }

    public ResponseTokenDTO login(LoginDTO loginDTO){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()
        ));

        UserDetails user = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow();

        String token = authenticationTokenService.getTokenWithoutClaims(user);
        ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO(token);

        return responseTokenDTO;
    }

    public void logout(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw  new IllegalArgumentException("Token invalid");
        }

        String refreshToken = authHeader.substring(7);

        authenticationTokenService.revokedToken(refreshToken);
    }
}
