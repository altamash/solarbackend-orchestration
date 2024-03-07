package com.microservice.commons.queue.service;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;

public interface QueuesService {

    Producer<String, String> createProducer();

    void sendMessage(String topic, String message);

    KafkaConsumer<String, String> createConsumer();
}
