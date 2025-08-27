package com.example.authentication.application.services;

import com.example.authentication.api.dto.GenerateCodeDTO;
import com.example.authentication.domain.models.GenerateCode;
import com.example.authentication.infraestructure.repository.GenerateCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class GenerateCodeService {

    private final GenerateCodeRepository generateCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String generatedCode(){
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(9000) + 1000;
        return String.valueOf(code);
    }

    public String generateCodeHashed(GenerateCodeDTO generateCodeDTO){
        String codeString = generatedCode();
        String codeHashed = bCryptPasswordEncoder.encode(codeString);
        GenerateCode generateCode = new GenerateCode();

        if (!generateCodeRepository.existsByEmail(generateCodeDTO.getEmail())){
            return "this email are not available";
        } else if (generateCodeRepository.existsByCode(generateCode.getCode())) {
            return "this email have a code currently";
        }

        generateCode.setEmail(generateCodeDTO.getEmail());
        generateCode.setCode(codeHashed);
        generateCode.setExpiration(LocalDateTime.now().plusMinutes(5));

        generateCodeRepository.save(generateCode);
        return "Email sended";
    }

    /*
    public boolean validateCode(String userEmail, String code){
        return false;
    }*/

}











