package com.back.bpo.labs.ticketing.platform.libs.exceptions;

public class TicketsNotAvailableException extends RuntimeException {
    public TicketsNotAvailableException(String message) {
        super(message);
    }
}