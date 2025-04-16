package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class EntidadInvalidaException extends RuntimeException {
    public EntidadInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }
}