package com.legalfriend.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.legalfriend.entities.Role;

/**
 * Created by deepak.j
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByRoleName(String roleName);

	List<Role> findAll();

	Role findById(Long roleId);
}
