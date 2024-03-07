package com.orchware.commons.module.streams.service;

import java.util.Arrays;

public enum EEventPosition {

    FROM_OFFSET("fromOffset"),
    FROM_OFFSET_INCLUSIVE("fromOffsetInclusive"),
    FROM_SEQUENCE_NUMBER("fromSequenceNumber"),
    FROM_SEQUENCE_NUMBER_INCLUSIVE("fromSequenceNumberInclusive"),
    FROM_ENQUEUED_TIME("fromEnqueuedTime"),
    FROM_START_OF_STREAM("fromStartOfStream"),
    FROM_END_OF_STREAM("fromEndOfStream");
    private String name;

    EEventPosition(String name) {
        this.name = name;
    }

    public static EEventPosition get(String name) {
        EEventPosition position = Arrays.stream(values()).filter(value -> name != null && name.equals(value.name)).findFirst().orElse(null);
        return position != null ? position : FROM_START_OF_STREAM;
    }

}
