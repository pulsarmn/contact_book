package com.pulsar.exception;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException() {
    }

    public ContactNotFoundException(Throwable cause) {
        super(cause);
    }

    public ContactNotFoundException(String message) {
        super(message);
    }

    public ContactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
