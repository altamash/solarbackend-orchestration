package com.orchware.core.exceptions;

import lombok.Getter;

@Getter
public class InvalidValueException extends OrchestrationException {

    private Long id;

    public InvalidValueException(String field, String value) {
        super(value + " is not a valid " + field);
    }

    public InvalidValueException(String field) {
        super(field + " is required");
    }
}
