package com.course.springbootepam.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.course.springbootepam.model.Message;
import com.course.springbootepam.repositories.MessageRepository;

@RestController
public class MessageController {
	
	@Autowired
	MessageRepository messageRepository;
	
	@GetMapping("/")
	public String index() {
		return String.format("Hello, %s!", "man");
	}
	
	@GetMapping("/messages")
	public Iterable<Message> messages() {
		return messageRepository.findAll();
	}
	
	@PostMapping("/messages")
	public Message messages(@RequestBody Message message) {
		return messageRepository.save(message);
	}
}
