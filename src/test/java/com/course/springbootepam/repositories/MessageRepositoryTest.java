package com.course.springbootepam.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.course.springbootepam.model.Message;

@DataJpaTest
class MessageRepositoryTest {
	
	@Autowired
	private MessageRepository repository;

	@Test
	void testSave() {
		Message message = new Message("test", "tester");
		repository.save(message);
		final Long id = repository.save(message).getId();
		message = repository.findById(id).orElseThrow();
		assertThat(message).isNotNull();
	}

}
