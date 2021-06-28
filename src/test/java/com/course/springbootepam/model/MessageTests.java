package com.course.springbootepam.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MessageTests {
	
	String result = "Message [id=null, message=test, author=tester]";

	@Test
	void messageToString() {
		Message message = new Message("test", "tester");
		assertThat(message.toString()).isEqualTo(result);
	}

}
