package com.doan.project.webadmin.projectAdmin.entities;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Event {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private String description;
	
	private String address;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime timeStart;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime timeEnd;
	
	@Column(columnDefinition = "text", length = 65535)
	private String image;
	
	private Integer umberUserJoin;
	
	private String status;
	
	private Integer idUser;
	
	public Event() {
		super();
	}

	public Event(Integer id, String name, String description, String address, LocalDateTime timeStart, LocalDateTime timeEnd,
			String image, Integer umberUserJoin, String status) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
		this.image = image;
		this.umberUserJoin = umberUserJoin;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(LocalDateTime timeStart) {
		this.timeStart = timeStart;
	}

	public LocalDateTime getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(LocalDateTime timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getUmberUserJoin() {
		return umberUserJoin;
	}

	public void setUmberUserJoin(Integer umberUserJoin) {
		this.umberUserJoin = umberUserJoin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}
	
}
