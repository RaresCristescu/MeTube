package com.app.metube.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
//		http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		http.authorizeHttpRequests((requests) -> requests.requestMatchers("/api/**").authenticated()
				.requestMatchers("/contact", "/error", "/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
						"/swagger-resources/**", "/configuration/ui", "/configuration/security", "/swagger-ui/**",
						"/webjars/**", "/swagger-ui.html", "/ws/**")
				.permitAll());
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

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user")
				.password("{noop}123").authorities("read").build();//$2a$12$7JUxqlNkImEiUXjXEsAwy.OZ5Go0tg9DmffX/3xN.HbjVPa7YwvMu
		UserDetails admin = User.withUsername("admin")
				.password("{bcrypt}$2a$12$X4flUx.23h1/GDdk1BvsqONeX3p0QatdMASCz0AB1gSzkOl50zD4G").authorities("admin").build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
//	@Bean //Comentat ca nu ma lasa cu parolele mele puse la misto gen 123 si admin
//	public CompromisedPasswordChecker compromisedPasswordChecker() {
//		return new HaveIBeenPwnedRestApiPasswordChecker();//verifica pe un api real de pe internet sa vada daca e ok parola
//	}

}
