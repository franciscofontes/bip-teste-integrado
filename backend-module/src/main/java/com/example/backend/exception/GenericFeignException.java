package com.example.backend.exception;

public class GenericFeignException extends RuntimeException {

    public GenericFeignException(String message) {
        super(message);
    }
}
