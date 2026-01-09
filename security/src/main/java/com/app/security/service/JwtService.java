package com.app.security.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.app.security.constants.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final UserDetailsService userDetailsService;
	private final SecretKey key;

	public JwtService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
		this.key = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(AppConstants.JWT_SECRET_DEFAULT_VALUE));
	}
	
	public String generateToken(Authentication authentication) {
		if (null != authentication) {
			return Jwts.builder()
						.issuer("MeTube")
						.subject("JWT Token")
						.claim("username", authentication.getName())
						.claim("roles",
								authentication.getAuthorities()
									.stream().map(GrantedAuthority::getAuthority)
									.toList())
						.issuedAt(new Date())
						.expiration(new Date((new Date()).getTime() + 30000000))
						.signWith(key)
						.compact();

		}
		return null;
	}
	
	 public Authentication validateToken(String token) {

	        Claims claims = Jwts.parser()
	        					.verifyWith(key)
	        					.build()
	        					.parseSignedClaims(token)
	        					.getPayload();
	          
	        String username = String.valueOf(claims.get("username"));
	        
	        UserDetails user =
	                userDetailsService.loadUserByUsername(username);
	        
			return  new UsernamePasswordAuthenticationToken(user, null, 
					user.getAuthorities());
	    }
}
