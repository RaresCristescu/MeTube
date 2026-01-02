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

@RestController
public class ContactRest {
//	private UserService userService;

//	@Autowired
//	public UserRest(UserService userService) {
//		this.userService = userService;
//	}

	@GetMapping("/contact")
	public String getContactInfo() {
		return "INTRA AICI CA SA BLA BLA";
	}

}
