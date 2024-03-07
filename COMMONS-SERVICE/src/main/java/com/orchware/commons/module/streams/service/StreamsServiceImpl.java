package com.orchware.commons.module.streams.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.azure.eventhubs.*;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class StreamsServiceImpl implements StreamsService {

    final java.net.URI namespace = new java.net.URI("sb://orchware-eventhub-namespace.servicebus.windows.net/;SharedAccessKeyName=authkeyeventhub;SharedAccessKey=2sWrjHeX8JFiIFDn93J0tysF2EUtzK7K1+AEhJkI4CY=;EntityPath=eventhub-instance-1"); // to target National clouds, change domain name too
    final String eventhub = "eventhub-instance-1";
    final String authority = "https://login.microsoftonline.com/1b10882f-6873-45a5-b4c6-c484fca221ed";
    final String clientId = "adba649e-c0d7-4aab-b712-30d7ca9dde90"; // not needed to run with Managed Identity
    final String clientSecret = "wgQ8Q~zo-DGCiRLoaEsaGWEXD9UZ7GAAgKMJGaMT"; // not needed to run with Managed Identity

    private ScheduledExecutorService executorService;
    private EventHubClient ehClient;
    //private static final ThreadLocal<ScheduledExecutorService> executorService = new ThreadLocal<>();
//    private static final ThreadLocal<EventHubClient> ehClient = new ThreadLocal<>();

    public StreamsServiceImpl() throws URISyntaxException {
    }

    private ScheduledExecutorService setScheduledExecutorService() {
        // The Executor handles all asynchronous tasks and this is passed to the EventHubClient instance.
        // This enables the user to segregate their thread pool based on the work load.
        // This pool can then be shared across multiple EventHubClient instances.
        // The following sample uses a single thread executor, as there is only one EventHubClient instance,
        // handling different flavors of ingestion to Event Hubs here.
        return Executors.newScheduledThreadPool(4);
    }

    private EHClient managedIdentityScenario() throws IOException, EventHubException {
        final ConnectionStringBuilder connStr = new ConnectionStringBuilder()
                .setEndpoint(this.namespace)
                .setEventHubName(this.eventhub)
                .setAuthentication(ConnectionStringBuilder.MANAGED_IDENTITY_AUTHENTICATION);
        ScheduledExecutorService executorService = setScheduledExecutorService();
        return new EHClient(executorService, EventHubClient.createFromConnectionStringSync(connStr.toString(), executorService));
    }

    private EHClient useAuthCallback() throws IOException, InterruptedException, ExecutionException, EventHubException {
        final AuthCallback callback = new AuthCallback(clientId, clientSecret);
        ScheduledExecutorService executorService = setScheduledExecutorService();
        return new EHClient(executorService, EventHubClient.createWithAzureActiveDirectory(namespace, eventhub, callback, authority, executorService, null).get());
    }

    private EHClient useAADTokenProvider() throws IOException, InterruptedException, ExecutionException, EventHubException {
        final AuthCallback callback = new AuthCallback(clientId, clientSecret);
        ScheduledExecutorService executorService = setScheduledExecutorService();
        final AzureActiveDirectoryTokenProvider aadTokenProvider = new AzureActiveDirectoryTokenProvider(callback, authority, null);
        return new EHClient(executorService,(EventHubClient.createWithTokenProvider(namespace, eventhub, aadTokenProvider, executorService, null).get()));
    }

    private EHClient useCustomTokenProvider() throws IOException, InterruptedException, ExecutionException, EventHubException {
        final CustomTokenProvider tokenProvider = new CustomTokenProvider(authority, clientId, clientSecret);
        ScheduledExecutorService executorService = setScheduledExecutorService();
        return new EHClient(executorService,EventHubClient.createWithTokenProvider(namespace, eventhub, tokenProvider, executorService, null).get());
    }

    /*private void sendReceive(EventHubClient ehClient, ScheduledExecutorService executorService) throws IOException, EventHubException {
        try {
            final Gson gson = new GsonBuilder().create();

            for (int i = 0; i < 100; i++) {

                String payload = "Message " + Integer.toString(i);
                byte[] payloadBytes = gson.toJson(payload).getBytes(Charset.defaultCharset());
                EventData sendEvent = EventData.create(payloadBytes);
                ehClient.sendSync(sendEvent);
            }

            System.out.println(Instant.now() + ": Send Complete...");

            final PartitionReceiver receiver = ehClient.createReceiverSync(
                    EventHubClient.DEFAULT_CONSUMER_GROUP_NAME,
                    "0",
                    EventPosition.fromStartOfStream());

            Iterable<EventData> receivedEvents = receiver.receiveSync(100);
            while (true) {
                int batchSize = 0;
                if (receivedEvents != null) {
                    for (final EventData receivedEvent : receivedEvents) {
                        if (receivedEvent.getBytes() != null)
                            System.out.println(String.format("Message Payload: %s", new String(receivedEvent.getBytes(), Charset.defaultCharset())));

                        System.out.println(String.format("Offset: %s, SeqNo: %s, EnqueueTime: %s",
                                receivedEvent.getSystemProperties().getOffset(),
                                receivedEvent.getSystemProperties().getSequenceNumber(),
                                receivedEvent.getSystemProperties().getEnqueuedTime()));
                        batchSize++;
                    }
                }
                else {
                    break;
                }

                System.out.println(String.format("ReceivedBatch Size: %s", batchSize));
                receivedEvents = receiver.receiveSync(100);
            }

            System.out.println(Instant.now() + ": Receive Complete...");

            System.out.println("Press Enter to stop.");
            System.in.read();
        } finally {
            ehClient.closeSync();
            executorService.shutdown();
        }
    }
*/
    private void send(int authOption, String payload) throws EventHubException {
        EHClient ehClient = authenticate(authOption);
        send(ehClient, payload);
    }

    private List<EventDataResponse> receive(int authOption, String eventPosition, String value, Boolean inclusive) throws EventHubException, IOException {
        EHClient ehClient = authenticate(authOption);
        return receive(ehClient, eventPosition, value, inclusive);
    }

    private EHClient authenticate(int authOption) {
        EAuthMethod method = EAuthMethod.get(authOption);
        try {
            switch (method) {
                case MANAGED_IDENTITY:
                    return managedIdentityScenario();
                case AAD_TOKEN_USING_AuthCallback:
                    return useAuthCallback();
                case AAD_TOKEN_USING_AzureActiveDirectoryTokenProvider:
                    return useAADTokenProvider();
                case AAD_TOKEN_USING_ITokenProvider:
                    return useCustomTokenProvider();
                default:
                    System.out.println("Unknown authentication method");
            }
        } catch (Exception ex) {
            System.out.println("Error during execution. Exception: " + ex.toString());
        }
        return null;
    }

    @Override
    public void sendEventData(int authOption, String data) throws EventHubException, IOException {
        send(authOption, data);
    }

    @Override
    public List<EventDataResponse> receiveEventData(int authOption, String eventPosition, String value, Boolean inclusive)
            throws EventHubException, IOException {
        return receive(authOption, eventPosition, value, inclusive);
    }

    private void send(EHClient ehClient, String payload) throws EventHubException {

        try {
            final Gson gson = new GsonBuilder().create();

            /*for (int i = 0; i < 100; i++) {

                String payload = "Message " + Integer.toString(i);
                byte[] payloadBytes = gson.toJson(payload).getBytes(Charset.defaultCharset());
                EventData sendEvent = EventData.create(payloadBytes);
                ehClient.get().sendSync(sendEvent);
            }*/
//            String payload = "Message " + Integer.toString(i);
            byte[] payloadBytes = gson.toJson(payload).getBytes(Charset.defaultCharset());
            EventData sendEvent = EventData.create(payloadBytes);
            ehClient.ehClient.sendSync(sendEvent);

            System.out.println(Instant.now() + ": Send Complete...");

            System.out.println("Press Enter to stop.");
//            System.in.read();
        } finally {
            if (ehClient != null && ehClient.ehClient != null) {
                ehClient.ehClient.closeSync();
            }
            if (ehClient != null && ehClient.executorService != null) {
                ehClient.executorService.shutdown();
            }
        }
    }

    private List<EventDataResponse> receive(EHClient ehClient, String eventPosition, String value, Boolean inclusive) throws EventHubException {
        List<EventDataResponse> dataResponseList = new ArrayList<>();
        try {
            final PartitionReceiver receiver = ehClient.ehClient.createReceiverSync(
                    EventHubClient.DEFAULT_CONSUMER_GROUP_NAME,
                    "0",
//                    EventPosition.fromStartOfStream());
//                    EventPosition.fromSequenceNumber(560l, true));
                    getEventPosition(eventPosition, value, inclusive));

            Iterable<EventData> receivedEvents = receiver.receiveSync(100);
            while (true) {
                int batchSize = 0;
                if (receivedEvents != null) {
                    for (final EventData receivedEvent : receivedEvents) {
                        EventDataResponse response = new EventDataResponse();
                        if (receivedEvent.getBytes() != null) {
                            System.out.println(String.format("Message Payload: %s", new String(receivedEvent.getBytes(), Charset.defaultCharset())));
                            response.setPayload(new String(receivedEvent.getBytes(), Charset.defaultCharset()));
                        }
                        System.out.println(String.format("Offset: %s, SeqNo: %s, EnqueueTime: %s",
                                receivedEvent.getSystemProperties().getOffset(),
                                receivedEvent.getSystemProperties().getSequenceNumber(),
                                receivedEvent.getSystemProperties().getEnqueuedTime()));
                        response.setOffset(receivedEvent.getSystemProperties().getOffset());
                        response.setSequenceNumber(receivedEvent.getSystemProperties().getSequenceNumber());
                        response.setEnqueuedTime(receivedEvent.getSystemProperties().getEnqueuedTime());
                        dataResponseList.add(response);
                        batchSize++;
                    }
                }
                else {
                    break;
                }

                System.out.println(String.format("ReceivedBatch Size: %s", batchSize));
                receivedEvents = receiver.receiveSync(100);
            }

            System.out.println(Instant.now() + ": Receive Complete...");

            System.out.println("Press Enter to stop.");
        } finally {
            if (ehClient != null && ehClient.ehClient != null) {
                ehClient.ehClient.closeSync();
            }
            if (ehClient != null && ehClient.executorService != null) {
                ehClient.executorService.shutdown();
            }
        }
        return dataResponseList;
    }

    private EventPosition getEventPosition(String eventPosition, String value, Boolean inclusive) {
        EEventPosition position = EEventPosition.get(eventPosition);
        switch (position) {
            case FROM_OFFSET:
                return EventPosition.fromOffset(value);
            case FROM_OFFSET_INCLUSIVE:
                return EventPosition.fromOffset(value, inclusive);
            case FROM_SEQUENCE_NUMBER:
                return EventPosition.fromSequenceNumber(Long.parseLong(value));
            case FROM_SEQUENCE_NUMBER_INCLUSIVE:
                return EventPosition.fromSequenceNumber(Long.parseLong(value), inclusive);
            case FROM_ENQUEUED_TIME:
                return EventPosition.fromEnqueuedTime(Instant.parse(value));
//            case FROM_START_OF_STREAM:
//                return EventPosition.fromStartOfStream();
            case FROM_END_OF_STREAM:
                return EventPosition.fromEndOfStream();
            default:
                return EventPosition.fromStartOfStream();
        }
    }

    record EHClient (ScheduledExecutorService executorService, EventHubClient ehClient) {
    }
}
