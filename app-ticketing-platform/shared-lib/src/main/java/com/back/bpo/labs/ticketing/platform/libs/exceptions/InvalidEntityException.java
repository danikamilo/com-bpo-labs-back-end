package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}