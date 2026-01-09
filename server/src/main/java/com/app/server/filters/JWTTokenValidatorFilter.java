package com.app.server.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.security.service.JwtService;
import com.app.server.constants.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTTokenValidatorFilter extends OncePerRequestFilter{
	
	private final JwtService jwtService;
	
	public JWTTokenValidatorFilter(JwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(AppConstants.JWT_HEADER);
		if(header != null && header.startsWith("Bearer ")) {
			try {
				String jwt = header.substring(7);
				Authentication authentication = jwtService.validateToken(jwt);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}catch(Exception e) {
				throw new BadCredentialsException("Invalid token received!");
			}
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/api/user/login");
	}

}
