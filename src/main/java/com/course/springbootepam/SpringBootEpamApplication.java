package com.course.springbootepam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.course.springbootepam.model.Message;
import com.course.springbootepam.repositories.MessageRepository;

@SpringBootApplication
public class SpringBootEpamApplication {
	
	Log log = LogFactory.getLog(getClass());
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootEpamApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner run(MessageRepository messageRepository) {
		return args -> {
			Message message = new Message("Hello Spring Data 2", "Antonio");
			messageRepository.save(message);
		};
	}
	
}
