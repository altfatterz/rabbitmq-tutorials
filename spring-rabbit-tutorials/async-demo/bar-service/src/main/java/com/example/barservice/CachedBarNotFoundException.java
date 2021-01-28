package com.example.barservice;

public class CachedBarNotFoundException extends RuntimeException {

    public CachedBarNotFoundException(String message) {
        super(message);
    }
}
