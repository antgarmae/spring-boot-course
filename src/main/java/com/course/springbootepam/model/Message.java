package com.course.springbootepam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue
	private Long id;
	private String message;
	private String author;
	
	public Message(String message, String author) {
		this.message = message;
		this.author = author;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", author=" + author + "]";
	}
}
