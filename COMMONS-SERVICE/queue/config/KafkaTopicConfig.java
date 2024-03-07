package com.microservice.commons.queue.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.core.KafkaAdmin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import static java.util.Map.*;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${topic.name.message}")
    private String topicName;
    final Properties props = loadConfig("client.properties");

    public KafkaTopicConfig() throws IOException {
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

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> result = new HashMap<>();
//                props.entrySet()
//                .stream()
//                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (x, y) -> y, HashMap::new));
        /*HashMap<Object, Object> result = props.entrySet()
                .stream()
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (prev, next) -> next, HashMap::new));*/
        props.entrySet().forEach(p -> result.put((String) p.getKey(), p.getValue()));
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, props.get("bootstrap.servers"));
        return new KafkaAdmin(result);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(topicName, 1, (short) 1);
    }

    /*@Bean
    public NewTopic topic5() {
        NewTopic newTopic = new NewTopic(longMsgTopicName, 1, (short) 1);
        Map<String, String> configs = new HashMap<>();
        configs.put("max.message.bytes", "20971520");
        newTopic.configs(configs);
        return newTopic;
    }*/

}
