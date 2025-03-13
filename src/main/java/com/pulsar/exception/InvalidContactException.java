package com.pulsar.exception;

import java.util.List;

public class InvalidContactException extends RuntimeException {

    private final List<String> errors;

    public InvalidContactException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
