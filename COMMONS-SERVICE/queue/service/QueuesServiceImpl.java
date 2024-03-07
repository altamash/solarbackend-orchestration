package com.microservice.commons.queue.service;

import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
public class QueuesServiceImpl implements QueuesService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private static final String ORCH_TOPIC = "orch_topic";
    final Properties props = loadConfig("client.properties");
    @Value(value = "${topic.name.message}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public QueuesServiceImpl() throws IOException {
    }
    public static Properties loadConfig(final String configFile) throws IOException {
        File file = new ClassPathResource(configFile).getFile();
        if (!file.exists()) {
            throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(file)) {
            cfg.load(inputStream);
        }
        return cfg;
    }

    @Override
    public Producer<String, String> createProducer() {
        Producer<String, String> producer = null;
        try {
            producer = new KafkaProducer<>(props);
            RandomStringUtils.randomAlphanumeric(15);
            producer.send(new ProducerRecord<>(ORCH_TOPIC,
                    "Key-" + RandomStringUtils.randomAlphanumeric(5),
                    "Value-" + RandomStringUtils.randomAlphanumeric(15)));
            producer.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return producer;
    }

    @Override
    public void sendMessage(String topic, String message) {
//		kafkaTemplate.send(topicName, message);
//        CompletableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(topicName, message);
    }

    public void sendMessage(String message) {
//        ListenableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(topicName, message);

        /*future.addListener(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + message + "] due to : " + ex.getMessage());
            }
        });*/
    }

    @Override
    public KafkaConsumer<String, String> createConsumer() {
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-java-getting-started");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(ORCH_TOPIC));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("key = %s, value = %s%n", record.key(), record.value());
            }
        }
    }

    @KafkaListener(topics = ORCH_TOPIC, groupId = "foo")
    public void listenGroupFoo(String message) {
        System.out.println("Received Message in group foo: " + message);
    }

}
