package com.app.metube.util;

import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec.JwtSpec;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {
	private final String SECRET_KEY = "your_secret_key";
	
//	public String generateToken(UserDetails userDetails) {
//		return JwtSpec.builder
//	}
}
