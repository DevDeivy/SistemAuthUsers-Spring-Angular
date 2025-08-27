package com.example.authentication.domain.models;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Table(name = "tbl_refreshToken")
@Data
public class RefreshToken implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "tokenRefresh")
    private String tokenRefresh;


    private Boolean expired;
    private Boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
