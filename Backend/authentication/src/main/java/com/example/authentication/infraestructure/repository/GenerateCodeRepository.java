package com.example.authentication.infraestructure.repository;

import com.example.authentication.domain.models.GenerateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenerateCodeRepository extends JpaRepository<GenerateCode, Integer> {
    boolean existsByEmail(String email);
    boolean existsByCode(String code);
}
