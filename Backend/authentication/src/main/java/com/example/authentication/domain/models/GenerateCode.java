package com.example.authentication.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_code")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email")
    private String email;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "expiration")
    private LocalDateTime expiration;
}
