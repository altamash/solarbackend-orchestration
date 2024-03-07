package com.orchware.core.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends OrchestrationException {

    private Long id;
    private String sId;

    public NotFoundException(Class clazz, Long id) {
        super(clazz.getSimpleName() + " with id '" + id + "' not found.");
        this.id = id;
    }

    public NotFoundException(Class clazz, String name, String value) {
        super(clazz.getSimpleName() + " with '" + name + "' '" + value + "' not found.");
    }

    public NotFoundException(String message) {
        super(message);
    }
}
