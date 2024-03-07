package com.orchware.core.exceptions;

public class OrchestrationException extends RuntimeException {

    public OrchestrationException(String message) {
        super(message);
    }

    public OrchestrationException(String message, Throwable e) {
        super(message, e);
    }
}
