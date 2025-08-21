package com.example.authentication.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
