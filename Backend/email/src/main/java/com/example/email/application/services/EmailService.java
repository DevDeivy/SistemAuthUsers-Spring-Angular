package com.example.email.application.services;

import com.example.email.api.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final MailSender mailSender;

    public void sendEmail(@RequestBody EmailDTO emailDTO){
        if(emailDTO.getEmailSend() == null){
            throw new IllegalArgumentException("email is empty");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(emailDTO.getTitle());
        message.setTo(emailDTO.getEmailSend());
        message.setText(emailDTO.getBody());
        mailSender.send(message);
    }
}
