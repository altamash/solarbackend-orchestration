package com.orchware.commons.module.streams.service.test;

import com.azure.messaging.eventhubs.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Arrays;
import java.util.List;

public class Sender {
//    private static final String connectionString = "Endpoint=sb://orchware-eventhub-namespace.servicebus.windows.net/;SharedAccessKeyName=authkeyeventhub;SharedAccessKey=2sWrjHeX8JFiIFDn93J0tysF2EUtzK7K1+AEhJkI4CY=;EntityPath=eventhub-instance-1";
//    private static final String eventHubName = "eventhub-instance-1";
    private static final String connectionString = "Endpoint=sb://ns-ms-si.servicebus.windows.net/;SharedAccessKeyName=saskey;SharedAccessKey=7Nzcg9hxaOH4uPvNeCA6TZc34EArjsRgN+AEhKXYIsk=;EntityPath=evhub-ms-si";
    private static final String eventHubName = "evhub-ms-si";

    public static void main(String[] args) {
        publishEvents();
    }

    /**
     * Code sample for publishing events.
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public static void publishEvents() {
        // create a producer client
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        // sample events in an array
        ObjectNode messageJson = new ObjectMapper().createObjectNode();
        messageJson.put("Grid", "220.5");
        messageJson.put("Volage-Phase1", "210.0");
        messageJson.put("Voltage-Phase3", "230.0");
        List<EventData> allEvents = Arrays.asList(new EventData(messageJson.toString()));
//        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));

        // create a batch
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            // try to add the event from the array to the batch
            if (!eventDataBatch.tryAdd(eventData)) {
                // if the batch is full, send it and then create a new batch
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
            producer.send(eventDataBatch);
        }
        producer.close();
    }
}