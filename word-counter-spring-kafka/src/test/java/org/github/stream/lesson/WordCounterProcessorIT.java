package org.github.stream.lesson;

import org.junit.jupiter.api.Test;
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
    @Container
    private static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("docker.redpanda.com/redpandadata/redpanda:v23.2.10"));
    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();

    public void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {

    }
}
