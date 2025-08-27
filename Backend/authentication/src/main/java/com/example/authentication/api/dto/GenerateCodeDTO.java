package com.example.authentication.api.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateCodeDTO {
    @Email
    private String email;
    private String code;
    private LocalDateTime expiration;
}
