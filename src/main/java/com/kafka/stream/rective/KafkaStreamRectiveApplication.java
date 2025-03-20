package com.kafka.stream.rective;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.kafka.stream.rective")
public class KafkaStreamRectiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamRectiveApplication.class, args);
	}
}
