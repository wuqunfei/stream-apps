package org.github.stream.lesson;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class WordConsumer {

    private final Logger logger = LoggerFactory.getLogger(WordConsumer.class);

    private CountDownLatch latch = new CountDownLatch(1);

    private ConsumerRecord<String, Long> record;

    @KafkaListener(topics = "output-topic")
    public void receive(ConsumerRecord<String, Long> consumerRecord) {
        logger.info("Got message = '{}'", consumerRecord.toString());
        record = consumerRecord;
        latch.countDown();
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public ConsumerRecord<String, Long> getRecord() {
        return record;
    }
}
