package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.NotificationType;

@Repository
public interface NotificationTypeRepository extends CrudRepository<NotificationType, Long> {

	NotificationType findById(Long id);
}