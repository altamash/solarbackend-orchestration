package com.orchware.commons.module.streams.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
//@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDataResponse {
    private String payload;
    private String offset;
    private long sequenceNumber;
    private Instant enqueuedTime;
}
