package com.doan.project.webadmin.projectAdmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doan.project.webadmin.projectAdmin.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{
	@Modifying
	@Query(value = "update event ev set ev.status = ?1 where ev.id = ?2", nativeQuery = true)
	int setStatusForEvent(String status, Integer id);
}
