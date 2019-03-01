package com.legalfriend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserType;

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, Long> {

}
