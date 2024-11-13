package com.metube.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.metube.entity.User;
import com.metube.repo.UserRepo;

@Service
public class MyUserDetailsService implements UserDetailsService{

	private final UserRepo userRepo;
	
	
	
	public MyUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByLogin(username);
		if(user.isEmpty()) {
			throw new UsernameNotFoundException("This user does not exist in the database");
		}
		
		return new UserPrincipal(user.get());
	}

}
