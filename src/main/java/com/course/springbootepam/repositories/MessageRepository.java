package com.course.springbootepam.repositories;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.course.springbootepam.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_READ)
	public Optional<Message> findById(Long messageId);
	
	@Transactional
	@Modifying
	@Query("update Message m set m.author = :author where m.id = :id")
	int updateMessageSetAuthorForId(@Param("author") String message, @Param("id") Long id);
}
