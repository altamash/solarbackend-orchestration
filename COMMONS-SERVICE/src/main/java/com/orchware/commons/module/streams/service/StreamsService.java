package com.orchware.commons.module.streams.service;

import com.microsoft.azure.eventhubs.EventHubException;

import java.io.IOException;
import java.util.List;

public interface StreamsService {

    void sendEventData(int authOption, String payload) throws EventHubException, IOException;

    List<EventDataResponse> receiveEventData(int authOption, String eventPosition, String value, Boolean inclusive) throws EventHubException, IOException;
}
