package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserLogin;

/**
 * Created by deepak.j
 */
@Repository
public interface UserLoginRepository extends CrudRepository<UserLogin, Long> {

	UserLogin findById(Long id);

	UserLogin findByUserLoginId(String username);

}
