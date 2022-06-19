package com.doan.project.web.project.repository;

import com.doan.project.web.project.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByNameRole(String nameRole);
}
