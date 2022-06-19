package com.doan.project.web.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doan.project.web.project.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	User findByEmail(String email);
}
