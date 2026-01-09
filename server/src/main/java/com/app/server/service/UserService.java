package com.app.server.service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.data.dto.UserDetailsDto;
import com.app.data.dto.UserDto;
import com.app.data.dto.UserLoginDto;
import com.app.data.entity.Role;
import com.app.data.entity.User;
import com.app.data.entity.UserRole;
import com.app.data.enums.RoleEnum;
import com.app.data.repo.RoleRepo;
import com.app.data.repo.UserRepo;

@Service
public class UserService {

	private final UserRepo userRepo;
	private final RoleRepo roleEepo;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepo userRepo, RoleRepo roleEepo, PasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.roleEepo = roleEepo;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetailsDto getAccountDetails(final UUID id) {
		User u = userRepo.findById(id).orElseThrow(NoSuchElementException::new);
		UserDetailsDto ud = UserDetailsDto.builder().login(u.getLogin()).email(u.getEmail())
				.role(u.getRole().iterator().next().getRole().getCode().name()).build();
		return ud;
	}

	public ResponseEntity<String> registerUser(@RequestBody UserDto user) {
		try {
			String hashPwd = passwordEncoder.encode(user.getPassword());

			User newUser = new User();
			newUser.setLogin(user.getLogin());
			newUser.setEmail(user.getEmail());
			newUser.setPassword(hashPwd);
			newUser = userRepo.save(newUser);

			Set<UserRole> userRoles = newUser.getRole();
			Role r = roleEepo.findByCode(RoleEnum.ROLE_USER).orElseThrow(NoSuchElementException::new);
			UserRole ur = new UserRole(newUser, r);
			userRoles.add(ur);

			newUser.setRole(userRoles);

			userRepo.save(newUser);

			if (newUser.getId() != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body("Given user detail arte succesfully registered");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User registration failed");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
