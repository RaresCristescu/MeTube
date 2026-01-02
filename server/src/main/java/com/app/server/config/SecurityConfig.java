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
import org.springframework.security.config.http.SessionCreationPolicy;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.app.server.exceptionhandling.CustomAccessDeniedHandler;
import com.app.server.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.app.server.filters.CsrfCookieFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
		
		http
//		.sessionManagement(smc -> smc//.sessionFixation(sfc -> sfc.newSession())
//				.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
		.securityContext(contextConfig->contextConfig.requireExplicitSave(false))
		.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
		.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())//requiresSecure
			.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					CorsConfiguration config = new CorsConfiguration();
					config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
					config.setAllowedMethods(Collections.singletonList("*"));
					config.setAllowCredentials(true);
					config.setAllowedHeaders(Collections.singletonList("*"));
					config.setMaxAge(3600L);
					return config;
				}
			}))
			.csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
//					.ignoringRequestMatchers("/register")
					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
			//			.csrf(csrfConfig -> csrfConfig.disable())
			.authorizeHttpRequests((requests) -> requests
			.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
								"/swagger-resources/**", "/configuration/ui", "/configuration/security",
								"/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/ws/**").permitAll()
			.requestMatchers("/contact", "/error", "/api/user/register", "/invalidSession").permitAll()
			.requestMatchers("/api/**").authenticated());
		http.formLogin(withDefaults());
		http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
//		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));// asta e
		// globala
		// //GLobal
		// config
		return http.build();

//		
//		// daca nu adaugi requiresChannel atunci o sa accepte si http si https
////		http.requiresChannel(rcc-> rcc.anyRequest().requiresSecure());//obliga https
//		http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());// obliga http
//
//		http.csrf(csrfConfig -> csrfConfig.disable());
//		http.authorizeHttpRequests((requests) -> requests
//				.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
//						"/configuration/ui", "/configuration/security", "/swagger-ui/**", "/webjars/**",
//						"/swagger-ui.html", "/ws/**")
//				.permitAll().requestMatchers("/contact", "/error", "/api/user/register").permitAll()
//				.requestMatchers("/api/**").authenticated());
//		http.formLogin(withDefaults());
//		http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
////		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));// asta e
//																												// globala
//																												// //GLobal
//																												// config
//		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
