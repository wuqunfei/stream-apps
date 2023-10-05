package org.github.stream.lesson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:19092", "port=19092" })
@SpringBootTest(classes = WordCounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordCounterProcessorIT {
    Logger logger = LoggerFactory.getLogger(WordCounterProcessorIT.class);

    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    @Test
    public void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {
        logger.info("HelloWord");
        kafkaTemplate.send("input-topic", "hello world");
        logger.info("Sent");
    }
}
