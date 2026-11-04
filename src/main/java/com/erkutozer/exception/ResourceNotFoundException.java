package com.erkutozer.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String error){
        super(error);
    }
}
