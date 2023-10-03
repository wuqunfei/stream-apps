package org.github.stream.lesson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
public class StreamsCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamsCloudApplication.class, args);
    }

}
