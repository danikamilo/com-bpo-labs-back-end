package com.back.bpo.labs.ticketing.platform.libs.exceptions;


/**
 * @author Daniel Camilo
 */
public class MongoWriteErrorException extends RuntimeException {
    public MongoWriteErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}