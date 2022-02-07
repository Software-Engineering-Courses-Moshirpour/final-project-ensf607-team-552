package com.repository;

import com.model.ERole;
import com.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends PagingAndSortingRepository<Role, Integer> {
    Optional<Role> findByRoleName(ERole roleName);
}
