package com.example.email.api.controllers;

import com.example.email.api.dto.EmailDTO;
import com.example.email.application.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<Object> sendEmail(@RequestBody EmailDTO emailDTO){
        emailService.sendEmail(emailDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email sended to " + emailDTO.getEmailSend());
    }
}
