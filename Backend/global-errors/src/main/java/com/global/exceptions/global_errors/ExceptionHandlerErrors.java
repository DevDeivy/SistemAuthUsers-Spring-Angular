package com.global.exceptions.global_errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
@Slf4j
@Component
public class ExceptionHandlerErrors {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionHandlerDTO> handlerExceptionError(MethodArgumentNotValidException exception){
        var response = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(error ->{
                var fieldName = error.getField();
                var message = error.getDefaultMessage();
                response.put(fieldName, message);
        });
        log.warn("Validators error: {}", exception.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionHandlerDTO(response));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionHandlerDTO> handlerExceptionError(Exception exception){
        var response = new HashMap<String, String>();
        var fielName = "message: ";
        var message = "please connect to de administrator or try more later";
        response.put(fielName, message);
        log.error("Validator error: {}" , exception.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionHandlerDTO(response));
    }

}
