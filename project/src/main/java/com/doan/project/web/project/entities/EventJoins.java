package com.doan.project.web.project.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class EventJoins {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String status;
	
	private String contibuteDevices;
	
	private String numberContribute;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_event", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Event event;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "id_user", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private User user;
	
	public EventJoins() {
		super();
	}

	public EventJoins(Integer id, String status, String contibuteDevices, String numberContribute, Event event,
			User user) {
		super();
		this.id = id;
		this.status = status;
		this.contibuteDevices = contibuteDevices;
		this.numberContribute = numberContribute;
		this.event = event;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContibuteDevices() {
		return contibuteDevices;
	}

	public void setContibuteDevices(String contibuteDevices) {
		this.contibuteDevices = contibuteDevices;
	}

	public String getNumberContribute() {
		return numberContribute;
	}

	public void setNumberContribute(String numberContribute) {
		this.numberContribute = numberContribute;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
