package com.app.server.security;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.data.entity.User;
import com.app.data.repo.UserRepo;

@Service
public class MeTubeUserDetailsService implements UserDetailsService {

	private final UserRepo userRepo;

	public MeTubeUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByLogin(username)
				.orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));

		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
		
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
				authorities);
	}

}
