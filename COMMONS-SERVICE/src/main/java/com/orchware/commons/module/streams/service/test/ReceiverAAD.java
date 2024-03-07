package com.orchware.commons.module.streams.service.test;

import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.*;
import com.azure.storage.blob.*;

import java.io.IOException;
import java.util.function.Consumer;

import com.azure.identity.*;

public class ReceiverAAD {

    private static final String namespaceName = "orchware-Eventhub-namespace.servicebus.windows.net";
    private static final String eventHubName = "eventhub-instance-1";

    public static void main(String[] args) throws IOException {
        /*System.setProperty("AZURE_CLIENT_ID", "749306f5-dfff-4339-a0ce-a137a25f04db");
        System.setProperty("AZURE_CLIENT_SECRET", "kpn8Q~dGS1PuWhR.Qr6kFg7S1i9zqcRSJU3CYdzA");
        System.setProperty("AZURE_TENANT_ID", "1b10882f-6873-45a5-b4c6-c484fca221ed");*/
        System.setProperty("AZURE_CLIENT_ID", "adba649e-c0d7-4aab-b712-30d7ca9dde90");
        System.setProperty("AZURE_CLIENT_SECRET", "wgQ8Q~zo-DGCiRLoaEsaGWEXD9UZ7GAAgKMJGaMT");
        System.setProperty("AZURE_TENANT_ID", "1b10882f-6873-45a5-b4c6-c484fca221ed");
        // create a token using the default Azure credential
        DefaultAzureCredential credential = new DefaultAzureCredentialBuilder()
                .authorityHost(AzureAuthorityHosts.AZURE_PUBLIC_CLOUD)
                .build();

// Create a blob container client that you use later to build an event processor client to receive and process events
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .credential(credential)
                .sasToken("?sv=2021-06-08&ss=bfqt&srt=sco&sp=rwdlacupiyx&se=2023-12-31T17:29:34Z&st=2023-01-02T09:29:34Z&spr=https&sig=Rm36UsxcFqzdFVi3n2tsQPIlmfpAlmvhhuMnpV%2Fp5zU%3D")
                .endpoint("https://devstoragesi.blob.core.windows.net")
                .containerName("streams")
                .buildAsyncClient();

// Create an event processor client to receive and process events and errors.
        EventProcessorClient eventProcessorClient = new EventProcessorClientBuilder()
                .fullyQualifiedNamespace(namespaceName)
                .eventHubName(eventHubName)
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .processEvent(PARTITION_PROCESSOR)
                .processError(ERROR_HANDLER)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient))
                .credential(credential)
                .buildEventProcessorClient();



        System.out.println("Starting event processor");
        eventProcessorClient.start();

        System.out.println("Press enter to stop.");
        System.in.read();

        System.out.println("Stopping event processor");
        eventProcessorClient.stop();
        System.out.println("Event processor stopped.");

        System.out.println("Exiting process");
    }

    public static final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
        PartitionContext partitionContext = eventContext.getPartitionContext();
        EventData eventData = eventContext.getEventData();

        System.out.printf("Processing event from partition %s with sequence number %d with body: %s%n",
                partitionContext.getPartitionId(), eventData.getSequenceNumber(), eventData.getBodyAsString());

        // Every 10 events received, it will update the checkpoint stored in Azure Blob Storage.
        if (eventData.getSequenceNumber() % 10 == 0) {
            eventContext.updateCheckpoint();
        }
    };

    public static final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
        System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    };
}
