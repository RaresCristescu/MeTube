package com.app.metube.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.metube.dto.UserDto;
import com.app.metube.entity.User;
import com.app.metube.enums.Role;
import com.app.metube.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo repo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
		super();
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}
	
	public ResponseEntity<String> registerUser(@RequestBody UserDto user) {
		try {
			String hashPwd = passwordEncoder.encode(user.getPassword());
			
			final User newUser = new User();
			newUser.setLogin(user.getLogin());
			newUser.setEmail(user.getEmail());
			newUser.setPassword(hashPwd);
			newUser.setRole(Role.ROLE_ADMIN);
			
			User savedUser = repo.save(newUser);
			
			if(savedUser.getId()!=null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Given user detail arte succesfully registered");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
			}
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
