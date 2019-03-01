package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {
	List<Notification> findByUserId(Long userId);
}