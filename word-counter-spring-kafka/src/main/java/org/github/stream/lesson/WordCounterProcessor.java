package org.github.stream.lesson;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class WordCounterProcessor {

    public static final String INPUT_TOPIC = "input-topic";
    public static final String OUTPUT_TOPIC = "output-topic";
    public static final String COUNTER_STORE = "counter-store";

    @Autowired
    void process(StreamsBuilder builder) {
        KStream<String, String> inputStream = builder.stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));
        KTable<String, Long> wordCounts = inputStream
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
                .groupBy((key, word) -> word, Grouped.with(Serdes.String(), Serdes.String()))
                .count(Materialized.as(COUNTER_STORE));
        KStream<String, Long> outputStream = wordCounts.toStream();
        outputStream.to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    }
}
