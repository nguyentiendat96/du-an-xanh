package com.doan.project.web.project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
	private Integer id;
	
	private String email;
	
	private String password;
	
	private String address;
	
	private String numberPhone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role", referencedColumnName = "id")
	private Role role;
	
	public User() {
		super();
	}

	public User(Integer id, String email, String password, String address, String numberPhone, Role role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.address = address;
		this.numberPhone = numberPhone;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumberPhone() {
		return numberPhone;
	}

	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
