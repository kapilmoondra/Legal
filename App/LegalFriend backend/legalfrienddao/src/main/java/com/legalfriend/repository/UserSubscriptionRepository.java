package com.legalfriend.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.User;
import com.legalfriend.entities.UserSubscription;

@Repository
public interface UserSubscriptionRepository extends CrudRepository<UserSubscription, Long> {

	UserSubscription findById(Long id);

	UserSubscription findByUser(User user);

	UserSubscription findByUserAndThruDate(User user, Date date);

}
