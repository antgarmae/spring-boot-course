package com.course.springbootepam.integration;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.course.springbootepam.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MessageIT {

	@Autowired
    private MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;

    @Test
    void requestProtectedUrlWithUser() throws Exception {
    	this.mvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello, man!"));
        
    }
    
    @Test
	void getMessages() throws Exception {
		this.mvc.perform(get("/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].author", is("Antonio")));
	}
	
    @Test
	void postMessages() throws Exception {
    	Message message = new Message("Hello Integration", "Me");
    	
    	MvcResult mvcResult = this.mvc.perform(post("/messages")
	    			.content(objectMapper.writeValueAsString(message))
	                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.author", is("Me")))
				.andReturn();
    	
    	String response = mvcResult.getResponse().getContentAsString();
    	Integer id = JsonPath.parse(response).read("$.id");
    	
    	this.mvc.perform(get("/messages"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[-1].id", is(id)));
	}

}
