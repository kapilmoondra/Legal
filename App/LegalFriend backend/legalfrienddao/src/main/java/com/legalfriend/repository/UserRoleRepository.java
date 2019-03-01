package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Role;
import com.legalfriend.entities.User;

@Repository
public interface UserRoleRepository extends CrudRepository<Role, Long> {

	String FETCH_USER_ROLE = " select r.role_name from user_role ur left join role r on r.id=ur.role_id WHERE ur.user_id = ?1";
	
	
	@Query(value= FETCH_USER_ROLE, nativeQuery = true)
	List<String> findUserRole(Long userId);
}
