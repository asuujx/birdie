package com.birdie.backend.exceptions;

public class EntityAlreadyExistException extends IllegalArgumentException {
    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
