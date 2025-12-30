package com.app.server.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.data.dto.UserDto;
import com.app.data.entity.User;
import com.app.data.enums.Role;
import com.app.data.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo repo;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepo repo, PasswordEncoder passwordEncoder) {
		super();
		this.repo = repo;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public ResponseEntity<String> getAccountDetails(){
		return null;
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
