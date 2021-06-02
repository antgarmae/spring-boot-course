package com.course.springbootepam.repositories;

import org.springframework.data.repository.CrudRepository;

import com.course.springbootepam.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
