package com.example.authentication.infraestructure.repository;

import com.example.authentication.domain.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {

    Optional<RefreshToken> findByTokenRefresh(String tokenRefresh);
}
