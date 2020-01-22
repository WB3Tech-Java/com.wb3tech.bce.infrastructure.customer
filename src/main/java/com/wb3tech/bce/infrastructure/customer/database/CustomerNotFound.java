package com.wb3tech.bce.infrastructure.customer.database;

public class CustomerNotFound extends RuntimeException {
    public CustomerNotFound(String format) { super (format); }
}
