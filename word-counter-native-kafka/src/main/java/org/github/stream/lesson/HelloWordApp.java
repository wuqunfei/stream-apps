package org.github.stream.lesson;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class HelloWordApp {


    public static void main(String[] args) {
        // Define Kafka Streams configuration
        Properties props = getProperties();

        StreamsBuilder builder = new StreamsBuilder();

        // Read from the input topic
        KStream<String, String> inputTopic = builder.stream("input-topic");

        // Transform the data: in this case, uppercase the value of each record
        KStream<String, String> transformedStream = inputTopic.mapValues(value -> value.toUpperCase());

        // Write the transformed data to the output topic
        transformedStream.to("output-topic");

        try (KafkaStreams streams = new KafkaStreams(builder.build(), props)) {

            // Start the Kafka Streams application
            streams.start();

            // Add a shutdown hook to gracefully close the streams application
            Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        }
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kstream-hello-word");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:19092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return props;
    }
}
