package com.course.springbootepam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.course.springbootepam.model.Message;
import com.course.springbootepam.repositories.MessageRepository;

@SpringBootApplication
public class SpringBootEpamApplication implements CommandLineRunner {
	
	Log log = LogFactory.getLog(getClass());
	
	@Autowired
	MessageRepository messageRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEpamApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Hello World: Starting app !!");
		
		Message message = new Message("Hello Spring Data", "Antonio");
		
		log.info("Creating message...");
		final Long id = messageRepository.save(message).getId();
		log.info("Created message with id: " + id);
		
		log.info("Updating message by Query...");
		int result = messageRepository.updateMessageSetAuthorForId("Garcia", id);
		log.info("Updated message: " + result);
		
		log.info("Reading message by Pessimistic lock...");
		message = messageRepository.findById(id).orElseThrow();
		log.info("Readed message: " + message);
		
		message.setMessage("Bye Spring Data");
		
		log.info("Updating message...");
		messageRepository.save(message);
		log.info("Updated message: " + message);
		
		log.info("Deleting message...");
		messageRepository.deleteById(id);
		log.info("Deleted message with Id: " + id);
		
		log.info("Processing finished !!");
	}

}
