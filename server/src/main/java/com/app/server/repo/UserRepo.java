package com.app.server.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.server.entity.MyUserDetails;

@Repository
public interface UserRepo extends JpaRepository<MyUserDetails,UUID>{
	
	Optional<MyUserDetails> findByLogin(String login);

}
