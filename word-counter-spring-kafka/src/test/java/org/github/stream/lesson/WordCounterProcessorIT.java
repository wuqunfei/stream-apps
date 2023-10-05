package org.github.stream.lesson;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:19092", "port=19092"})
@ContextConfiguration(classes = {KafkaAdminConfig.class, WordCounterProcessor.class,})
@SpringBootTest(classes = WordCounterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordCounterProcessorIT {
    Logger logger = LoggerFactory.getLogger(WordCounterProcessorIT.class);

    @Autowired
    private WordProcessor wordProcessor;

    @Autowired
    private WordConsumer wordConsumer;

    @Test
    @Ignore
    public void givenInputMessages_whenPostToEndpoint_thenWordCountsReceivedOnOutput() throws Exception {
        wordProcessor.send("input-topic", "hello world");
        boolean consumed = wordConsumer.getLatch().await(10, TimeUnit.SECONDS);
        logger.info(wordConsumer.getRecord().topic());
    }

}
