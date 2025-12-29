package com.app.server.config;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.server.security.MeTubeUserDetailsService;

@Component
@Profile("!prod")
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider{

	private final MeTubeUserDetailsService meTubeUserDetailsService;
	private final PasswordEncoder passwordEncoder;
	
	public UsernamePwdAuthenticationProvider(final MeTubeUserDetailsService meTubeUserDetailsService,
			final PasswordEncoder passwordEncoder) {
		super();
		this.meTubeUserDetailsService = meTubeUserDetailsService;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails userDetails = meTubeUserDetailsService.loadUserByUsername(username);
		if(passwordEncoder.matches(password, userDetails.getPassword())) {
			//poti sa adaugi extra verificati cum ar fi sa validezi ca varsta e > 18 ani.
			return new UsernamePasswordAuthenticationToken(username,password, userDetails.getAuthorities());
		} else {
			throw new BadCredentialsException("Invalid password");
		}
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
