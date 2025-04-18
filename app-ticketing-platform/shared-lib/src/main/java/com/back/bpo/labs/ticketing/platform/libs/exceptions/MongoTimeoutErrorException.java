package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class MongoTimeoutErrorException extends RuntimeException {
    public MongoTimeoutErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}