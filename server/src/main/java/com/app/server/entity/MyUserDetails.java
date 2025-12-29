package com.app.server.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.data.entity.User;
import com.app.data.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@AllArgsConstructor
public class MyUserDetails extends User implements UserDetails {
	private static final long serialVersionUID = -1353883722173763047L;

//
//	public MyUserDetails(UUID id, Date creation, Date expires, Date modified, String login, String email,
//			String password, Role role) {
//		super(id, creation, expires, modified, login, email, password, role);
//	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return role.getAuthorities();
		return List.of(new SimpleGrantedAuthority(this.getRole().name()));
	}

	@Override
	public String getUsername() {
		return this.getLogin();
	}
	
}
