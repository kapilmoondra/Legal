package com.legalfriend.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.UserRelationship;

@Repository
public interface UserRelationshipRepository extends CrudRepository<UserRelationship, Long> {

	UserRelationship findByToUserIdAndToRoleIdAndFromRoleIdAndThruDate(Long userId, String name, String name2, Date date);

}
