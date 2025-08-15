package com.example.authentication.api.controllers;

import com.example.authentication.api.dto.GenerateCodeDTO;
import com.example.authentication.application.services.GenerateCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
public class GenerateCodeController {

    private final GenerateCodeService generateCodeService;

    @GetMapping("/")
    public ResponseEntity<Object> getCode(){
        return ResponseEntity.ok(generateCodeService.generatedCode());
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendCodetoEmail(@RequestBody @Valid GenerateCodeDTO generateCodeDTO){
        return ResponseEntity.ok(generateCodeService.generateCodeHashed(generateCodeDTO));
    }

}
