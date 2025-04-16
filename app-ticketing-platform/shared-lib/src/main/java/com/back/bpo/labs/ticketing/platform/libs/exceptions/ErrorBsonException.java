package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class ErrorBsonException extends RuntimeException {
    public ErrorBsonException(String message, Throwable cause) {
        super(message, cause);
    }
}