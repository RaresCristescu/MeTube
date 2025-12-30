package com.app.server.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.UserDto;
import com.app.data.entity.User;
import com.app.server.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRest {
	private UserService userService;

	public UserRest(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/users")
	public List<User> getUsers() {
//		return userService.getUsers();
		return null;
	}
	
	@GetMapping("/myAccount")
	public ResponseEntity<String> getAccountDetails() {
		return userService.getAccountDetails();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable("Id") UUID id) {
//		return userService.getUser(id);
		return null;
	}

	@PutMapping("/users/{id}")
	public User updateUser(@RequestBody User user, @PathVariable("Id") UUID id) {
//		return userService.updateUser(user, id);
		return null;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
		try {
			return userService.registerUser(userDto);
		}catch(Exception e) {
//			throw e;
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
