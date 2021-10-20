package com.transactionauthorizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class TransactionAuthorizerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionAuthorizerApiApplication.class, args);
	}

	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper().registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

}
