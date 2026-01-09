package com.app.server.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.data.dto.JwtResponse;
import com.app.data.dto.UserAuthenticationDto;
import com.app.data.dto.UserDetailsDto;
import com.app.data.dto.UserDto;
import com.app.data.entity.User;
import com.app.security.service.JwtService;
import com.app.server.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationRest {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationRest(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthenticationDto request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        String token = jwtService.generateToken(authentication);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
