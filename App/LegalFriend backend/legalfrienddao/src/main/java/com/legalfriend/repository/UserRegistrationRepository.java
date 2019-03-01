package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserRegisterToken;

/**
 * Created by deepak.j
 */
@Repository
public interface UserRegistrationRepository extends CrudRepository<UserRegisterToken, Long> {

	List<UserRegisterToken> findByUserId(Long username);

	UserRegisterToken findByToken(String token);
}
