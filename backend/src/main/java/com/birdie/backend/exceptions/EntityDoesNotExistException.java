package com.birdie.backend.exceptions;

public class EntityDoesNotExistException extends IllegalArgumentException {
    public EntityDoesNotExistException(String message) {
        super(message);
    }
}
