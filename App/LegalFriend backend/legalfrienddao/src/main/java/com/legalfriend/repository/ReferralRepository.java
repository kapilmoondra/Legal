package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Referral;

@Repository
public interface ReferralRepository extends CrudRepository<Referral, Long> {

	Referral findById(Long id);

	List<Referral> findByReferrerId(Long referrerId);

	Referral findByEmailAndReferrerId(String email, Long referrerId);

	List<Referral> findAll();
}
