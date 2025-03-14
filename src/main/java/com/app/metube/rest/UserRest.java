package com.app.metube.rest;

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

import com.app.metube.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserRest {
//	private UserService userService;

//	@Autowired
//	public UserRest(UserService userService) {
//		this.userService = userService;
//	}

	@GetMapping("/users")
	public List<User> getUsers() {
//		return userService.getUsers();
		return null;
	}
	
	@GetMapping("/myAccount")
	public String getAccountDetails() {
//		return userService.getUsers();
		return null;
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
	public ResponseEntity<User> updateUser(@RequestBody User user) {
//		User newUser = userService.addUser(user);
//		return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
		return null;
	}

}
