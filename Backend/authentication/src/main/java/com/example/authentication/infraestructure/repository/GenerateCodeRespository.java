package com.example.authentication.infraestructure.repository;

import com.example.authentication.domain.models.GenerateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateCodeRespository extends JpaRepository<GenerateCode, Integer> {
    Boolean existsByEmail(String email);
    Boolean existsByCode(String code);
}
