package com.app.data.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.app.data.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends CommonEntity{
	private static final long serialVersionUID = -1353883722173763047L;

	@Column(unique = true)
	private String login;
	
	private String email;

	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	

	public User(UUID id, Date creation, Date expires, Date modified, String login, String email, String password,
			Role role) {
		super(id, creation, expires, modified);
		this.login = login;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
