package com.example.authentication.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @NotNull
    private String firstName;
    private String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
}
