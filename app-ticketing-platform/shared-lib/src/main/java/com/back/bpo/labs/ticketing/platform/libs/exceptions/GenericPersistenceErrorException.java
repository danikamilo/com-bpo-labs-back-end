package com.back.bpo.labs.ticketing.platform.libs.exceptions;

public class GenericPersistenceErrorException extends RuntimeException {
    public GenericPersistenceErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
