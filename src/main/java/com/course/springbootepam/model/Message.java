package com.course.springbootepam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.AccessLevel;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

	@With
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
