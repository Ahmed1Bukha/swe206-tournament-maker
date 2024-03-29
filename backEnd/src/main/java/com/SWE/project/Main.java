package com.SWE.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;

@SpringBootApplication
public class Main {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(Main.class, args);
	}

}
