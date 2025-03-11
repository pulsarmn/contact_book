package com.pulsar.exception;

public class DuplicateContactException extends RuntimeException {

    public DuplicateContactException() {
    }

    public DuplicateContactException(Throwable cause) {
        super(cause);
    }

    public DuplicateContactException(String message) {
        super(message);
    }

    public DuplicateContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
