package com.microservice.commons.queue.controller;

import com.microservice.commons.queue.service.QueuesService;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController("QueueController")
@RequestMapping(value = "/queue")
public class QueuesController {

    private final QueuesService queuesService;

    public QueuesController(QueuesService queuesService) {
        this.queuesService = queuesService;
    }


    @GetMapping("/createProducer")
    public Producer<String, String> createProducer() {
        return queuesService.createProducer();
    }

    @PostMapping(value = "/message/topic/{topic}", produces = {MediaType.APPLICATION_JSON_VALUE})
    void sendToTopic(@RequestBody String message, @PathVariable String topic) {
//        queuesService.sendToTopic("producer1", topic, message);
        queuesService.sendMessage(topic, message);
    }

    @GetMapping("/createConsumer")
    public KafkaConsumer<String, String> createConsumer() {
        return queuesService.createConsumer();
    }
}
