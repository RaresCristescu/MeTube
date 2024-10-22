package com.metube.entity;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends CommonEntity{
	private static final long serialVersionUID = -1353883722173763047L;

	@Column(unique = true)
	private String login;
	
	private String password;

	public User(UUID id, Date creation, Date expires, Date modified, String login, String password) {
		super(id, creation, expires, modified);
		this.login = login;
		this.password = password;
	}

	public User() {
		super();
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
