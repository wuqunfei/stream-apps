package org.github.stream.lesson;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

public class WordCounterApp {
    public static void main(String[] args) {

        Properties configuration = createProperties();
        StreamsBuilder builder = new StreamsBuilder();
        Topology topology = createTopology(builder);

        KafkaStreams streams = new KafkaStreams(topology, configuration);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    public static Properties createProperties() {
        Properties properties = new Properties();
        String bootstrapServers = "localhost:19092";
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "native-kafka-app");
        properties.put(StreamsConfig.CLIENT_ID_CONFIG, "native-kafka-client");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "native-kafka-group");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        return properties;
    }

    public static Topology createTopology(StreamsBuilder builder) {
        String inputTopic = "input-native-topic";
        String outputTopic = "output-native-topic";
        KStream<String, String> textLines = builder.stream(inputTopic);
        KTable<String, Long> wordCounts = textLines
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as("native-store"));
        KStream<String, Long> outputStream = wordCounts.toStream();
        outputStream.peek((word, counter) -> System.out.println(word + ":" + counter))
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.Long()));
        return builder.build();
    }
}
