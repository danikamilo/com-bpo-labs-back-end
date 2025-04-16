package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class ErrorMongoEscrituraException extends RuntimeException {
    public ErrorMongoEscrituraException(String message, Throwable cause) {
        super(message, cause);
    }
}