package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserPasswordToken;

/**
 * Created by deepak.j
 */
@Repository
public interface UserPasswordLoginRepository extends CrudRepository<UserPasswordToken, Long> {

	@Query(value = "SELECT * FROM user_password_token WHERE fk_user_id = ?1 and expiry_date_time > now()", nativeQuery = true)
	List<UserPasswordToken> findByUserId(Long username);
}
