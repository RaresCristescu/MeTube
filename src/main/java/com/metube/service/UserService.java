package com.metube.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.metube.entity.User;
import com.metube.repo.UserRepo;

@Service
public class UserService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	final UserRepo userRepo;

	public UserService(UserRepo userRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepo = userRepo;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	public List<User> getUsers() {
		return userRepo.findAll();
	}

	public User getUser(UUID id) {
		final Optional<User> user = userRepo.findById(id);
		return user.isEmpty() ? null : user.get();
	}

	public User updateUser(User userDto, UUID id) {
		final Optional<User> dbUser = userRepo.findById(id);
		final User user = dbUser.get();
		user.setLogin(userDto.getLogin());
		user.setPassword(userDto.getPassword());
		userRepo.save(user);
		return user;
	}

	public User addUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	public void deleteUser(User user) {
		user.setExpires(new Date());
		userRepo.delete(user);
	}

}
