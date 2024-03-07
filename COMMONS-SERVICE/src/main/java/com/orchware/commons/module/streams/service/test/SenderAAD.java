package com.orchware.commons.module.streams.service.test;

import com.azure.messaging.eventhubs.*;
import java.util.Arrays;
import java.util.List;

import com.azure.identity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SenderAAD {

    // replace <NAMESPACE NAME> with the name of your Event Hubs namespace.
    // Example: private static final String namespaceName = "contosons.servicebus.windows.net";
    private static final String namespaceName = "orchware-Eventhub-namespace.servicebus.windows.net";

    // Replace <EVENT HUB NAME> with the name of your event hug.
    // Example: private static final String eventHubName = "ordersehub";
    private static final String eventHubName = "eventhub-instance-1";

    public static void main(String[] args) {
        /*System.setProperty("AZURE_CLIENT_ID", "749306f5-dfff-4339-a0ce-a137a25f04db");
        System.setProperty("AZURE_CLIENT_SECRET", "kpn8Q~dGS1PuWhR.Qr6kFg7S1i9zqcRSJU3CYdzA");
        System.setProperty("AZURE_TENANT_ID", "1b10882f-6873-45a5-b4c6-c484fca221ed");*/
        System.setProperty("AZURE_CLIENT_ID", "adba649e-c0d7-4aab-b712-30d7ca9dde90");
        System.setProperty("AZURE_CLIENT_SECRET", "nt48Q~G5cj0mn6z~4g-~5Vz.c91f3Ue3iHpBxaeS");
        System.setProperty("AZURE_TENANT_ID", "1b10882f-6873-45a5-b4c6-c484fca221ed");
        publishEvents();
    }
    /**
     * Code sample for publishing events.
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public static void publishEvents() {
        // create a token using the default Azure credential
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .authorityHost(AzureAuthorityHosts.AZURE_PUBLIC_CLOUD)
                .build();

        // create a producer client
        EventHubProducerClient producer = new EventHubClientBuilder()
                .fullyQualifiedNamespace(namespaceName)
                .eventHubName(eventHubName)
                .credential(credential)
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