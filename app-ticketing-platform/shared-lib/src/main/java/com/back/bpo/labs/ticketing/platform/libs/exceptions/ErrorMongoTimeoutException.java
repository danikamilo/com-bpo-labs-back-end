package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class ErrorMongoTimeoutException extends RuntimeException {
    public ErrorMongoTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}