package com.shivam.catalogservice.domain;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends RuntimeException{

    @Getter
    private final HttpStatus status ;

    public ProductNotFoundException(String message){
        super(message);
        this.status =HttpStatus.NOT_FOUND ;
    }
}
