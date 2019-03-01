package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.SubscriptionMaster;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionMaster, Long> {

	SubscriptionMaster findById(Long id);

}
