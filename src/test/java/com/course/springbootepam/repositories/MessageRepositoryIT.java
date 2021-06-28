package com.course.springbootepam.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.course.springbootepam.model.Message;

@DataJpaTest
class MessageRepositoryIT {
	
	@Autowired
	private MessageRepository repository;

	@Test
	void findById() {
		Message message = new Message("test", "tester");
		repository.save(message);
		final Long id = repository.save(message).getId();
		message = repository.findById(id).orElseThrow();
		assertThat(message).isNotNull();
	}
	
	@Test
	void updateMessageSetAuthorForId() {
		Message message = repository.findAll().iterator().next();
		Long id = message.getId();
		repository.updateMessageSetAuthorForId("updated", id);
		Message updatedMessage = repository.findById(id).get();
		assertThat(updatedMessage.getMessage()).isEqualTo("updated");
	}

}
