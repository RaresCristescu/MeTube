package com.metube.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.metube.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User,UUID>{
	
	Optional<User> findByLogin(String login);

}
