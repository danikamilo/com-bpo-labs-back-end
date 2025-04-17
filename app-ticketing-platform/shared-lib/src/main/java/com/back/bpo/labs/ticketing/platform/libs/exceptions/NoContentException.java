package com.back.bpo.labs.ticketing.platform.libs.exceptions;

/**
 * @author Daniel Camilo
 */
public class NoContentException extends RuntimeException {

    public NoContentException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoContentException(String message) {
        super(message);
    }
}
