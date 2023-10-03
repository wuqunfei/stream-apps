package org.github.stream.lesson;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafka
@EnableKafkaStreams
public class WordCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordCounterApplication.class, args);
    }

}
