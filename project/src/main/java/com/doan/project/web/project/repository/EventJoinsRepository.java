package com.doan.project.web.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.doan.project.web.project.entities.EventJoins;

@Repository
public interface EventJoinsRepository extends JpaRepository<EventJoins, Integer>{
	@Modifying
	@Query(value = "delete from doan.event_joins evjs where evjs.id_user = ?1 and evjs.id_event = ?2", nativeQuery = true)
	void deleteEventJoins(Integer idUser, Integer idEvent);
	
	@Modifying
	@Query(value = "update event_joins evjs set evjs.status = ?3 where evjs.id_user = ?1 and evjs.id_event = ?2", nativeQuery = true)
	int approvalEventJoins(Integer idUser, Integer idEvent, String status);
	
	@Query(value = "select * from event_joins evjs where evjs.id_user = ?1 and evjs.id_event = ?2", nativeQuery = true)
	EventJoins getEventJoins(Integer idUser, Integer idEvent);
	
	@Query(value = "select * from event_joins evjs join event evt on evjs.id_event = evt.id where evt.id_user = ?1 and evjs.status = '0'", nativeQuery = true)
	List<EventJoins> getEventNeedApproval(Integer idUser);

}
