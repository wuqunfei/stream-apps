package org.github.stream.lesson;

import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.apache.kafka.streams.state.KeyValueStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterProcessorUnitTest {

    @Test
    public void giveInputMsg_thenReceiveOnOutput() {

        StreamsBuilder streamsBuilder = new StreamsBuilder();
        WordCounterProcessor processor = new WordCounterProcessor();
        processor.process(streamsBuilder);
        Topology topology = streamsBuilder.build();

        try (TopologyTestDriver testDriver = new TopologyTestDriver(topology, new Properties())) {
            TestInputTopic<String, String> inputTopic = testDriver.createInputTopic(
                    WordCounterProcessor.INPUT_TOPIC,
                    new StringSerializer(),
                    new StringSerializer()
            );
            TestOutputTopic<String, Long> outputTopic = testDriver.createOutputTopic(
                    WordCounterProcessor.OUTPUT_TOPIC,
                    new StringDeserializer(),
                    new LongDeserializer());

            inputTopic.pipeInput("", "hello world");
            inputTopic.pipeInput("", "hello munich");


            KeyValueStore<String, Long> keyValueStore = testDriver.getKeyValueStore(WordCounterProcessor.COUNTER_STORE);

            assertEquals(2L,keyValueStore.get("hello"));
            assertEquals(1L,keyValueStore.get("world"));
            assertEquals(1L,keyValueStore.get("munich"));


            List<KeyValue<String, Long>> expectation = List.of(
                    KeyValue.pair("hello", 1L),
                    KeyValue.pair("world", 1L),
                    KeyValue.pair("hello", 2L),
                    KeyValue.pair("munich", 1L)
            );

            List<KeyValue<String, Long>> transformedValues = outputTopic.readKeyValuesToList();
            assertThat(transformedValues).hasSameElementsAs(expectation);

        }

    }

    @AfterEach
    void tearDown() {

    }

    @BeforeEach
    void setUp() {

    }
}
