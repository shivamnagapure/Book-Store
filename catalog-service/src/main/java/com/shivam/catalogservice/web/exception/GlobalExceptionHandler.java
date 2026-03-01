package com.shivam.catalogservice.web.exception;

import com.shivam.catalogservice.domain.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GlobalExceptionHandler {

    //Use ProblemDetails Object Fro Exception description
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> handleUnhandledException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }


    @ExceptionHandler(ProductNotFoundException.class)
    ResponseEntity<String> productNotFoundException(ProductNotFoundException exception){
        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
