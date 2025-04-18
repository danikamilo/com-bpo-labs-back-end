package com.back.bpo.labs.ticketing.platform.libs.enums;

public enum Status {
    CREATED("CREATED"),
    CONFIRMED("CONFIRMED"),
    PAID("PAID"),
    INVENTORY_UPDATED("INVENTORY_UPDATED"),
    PAYMENT_FAILED("PAYMENT_FAILED"),
    CANCELLED("CANCELLED");

    private String description;

    Status(String description) {
        this.description = description;
    }

    public String getValue() {
        return description;
    }
}