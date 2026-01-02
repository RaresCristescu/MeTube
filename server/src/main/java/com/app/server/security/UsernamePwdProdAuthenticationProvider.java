package com.app.server.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class UsernamePwdProdAuthenticationProvider implements AuthenticationProvider {

	private final MeTubeUserDetailsService meTubeUserDetailsService;
	private final PasswordEncoder passwordEncoder;

	public UsernamePwdProdAuthenticationProvider(final MeTubeUserDetailsService meTubeUserDetailsService,
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
		return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
