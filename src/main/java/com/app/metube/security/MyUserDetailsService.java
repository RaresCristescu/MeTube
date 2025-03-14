package com.app.metube.security;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.metube.entity.User;
import com.app.metube.repo.UserRepo;

//@Service
public class MyUserDetailsService implements UserDetailsService {

	private final UserRepo userRepo;

	public MyUserDetailsService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByLogin(username);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("This user does not exist in the database");
		}

		User dbUser = user.get();

		return new org.springframework.security.core.userdetails.User(dbUser.getUsername(), dbUser.getPassword(),
				Arrays.asList(dbUser.getRole().name()).stream().map(SimpleGrantedAuthority::new).toList());
	}

}
