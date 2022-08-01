package com.example.Ecoboard.Ecoboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomServiceException extends RuntimeException {
    public CustomServiceException(String message){
        super(message);
    }

}
