package com.orchware.core.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends OrchestrationException {

    private Long id;

    public ForbiddenException(String field, Long value) {
        super("Invalid " + field + ": " + value);
        this.id = value;
    }
}
