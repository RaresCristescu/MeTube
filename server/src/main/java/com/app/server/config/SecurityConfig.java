package com.app.server.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;



@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		//daca nu adaugi requiresChannel atunci o sa accepte si http si https
//		http.requiresChannel(rcc-> rcc.anyRequest().requiresSecure());//obliga https
		http.requiresChannel(rcc-> rcc.anyRequest().requiresInsecure());//obliga http
		
		http.csrf(csrfConfig->csrfConfig.disable());
		http.authorizeHttpRequests((requests) -> 
			requests
			.requestMatchers( "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
					"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**",
					"/webjars/**", "/swagger-ui.html", "/ws/**").permitAll()
			.requestMatchers("/contact", "/error", "/api/user/register").permitAll()
			.requestMatchers("/api/**").authenticated()
				);
//		http.formLogin(flc -> {
//			flc.disable();//daca sunt ambele disbled imi pica
//		});
//		http.httpBasic(hbc -> {
//			hbc.disable();//daca sunt abmele disalbed imi pica
//		});
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}

//	@Bean //mai am un bean MyUserDetailsService.java
//	public UserDetailsService userDetailsService(DataSource dataSource) {
//		return new JdbcUserDetailsManager(dataSource);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
//	@Bean //Comentat ca nu ma lasa cu parolele mele puse la misto gen 123 si admin
//	public CompromisedPasswordChecker compromisedPasswordChecker() {
//		return new HaveIBeenPwnedRestApiPasswordChecker();//verifica pe un api real de pe internet sa vada daca e ok parola
//	}

}
