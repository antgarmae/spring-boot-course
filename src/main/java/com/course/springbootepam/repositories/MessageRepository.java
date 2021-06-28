package com.course.springbootepam.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.course.springbootepam.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
	public Optional<Message> findById(Long messageId);
	
	@Modifying(clearAutomatically = true)
	@Query("update Message m set m.message = :message where m.id = :id")
	int updateMessageSetAuthorForId(@Param("message") String message, @Param("id") Long id);
}
