package org.github.stream.lesson;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//https://livebook.manning.com/book/kafka-streams-in-action/chapter-8/28
@Testcontainers
@SpringBootTest(classes = WordCounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordCounterProcessorIT {
    Logger logger = LoggerFactory.getLogger(WordCounterProcessorIT.class);
    @Container
    private static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("docker.redpanda.com/redpandadata/redpanda:v23.2.10")
                    .asCompatibleSubstituteFor("confluentinc/cp-kafka"));
    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {
        logger.info("HelloWord");
    }
}
