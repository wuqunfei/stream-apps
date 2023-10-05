package org.github.stream.lesson;


import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class WordProcessor {
    private final Logger logger = LoggerFactory.getLogger(WordProcessor.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Faker faker = new Faker();

    @Autowired
    public WordProcessor(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void send(String topic, String message) {
        logger.info("Send Message to topic {}: {}", topic, message);
        kafkaTemplate.send(topic, message);
    }
}
