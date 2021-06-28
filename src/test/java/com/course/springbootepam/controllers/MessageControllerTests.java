package com.course.springbootepam.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.course.springbootepam.model.Message;
import com.course.springbootepam.repositories.MessageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(MessageController.class)
class MessageControllerTests {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	private MessageRepository repository;
	
    @Test
    void requestProtectedUrlWithUser() throws Exception {
    	this.mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, man!"));
        
    }
    
    @Test
	void getMessages() throws Exception {
    	given(repository.findAll()).willReturn(List.of());
    	
		this.mvc.perform(get("/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(List.of())));
	}
	
    @Test
	void postMessages() throws Exception {
    	Message message = new Message("Hello Mocked", "Mocker");
    	given(repository.save(message)).willReturn(message.withId(1L));
    	
    	this.mvc.perform(post("/messages")
	    			.content(objectMapper.writeValueAsString(message))
	                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)));
	}

}
