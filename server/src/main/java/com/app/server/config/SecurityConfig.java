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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
import com.app.server.filters.JWTTokenValidatorFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {
	private final JWTTokenValidatorFilter jWTTokenValidatorFilter;

	public SecurityConfig(JWTTokenValidatorFilter jWTTokenValidatorFilter) {
		this.jWTTokenValidatorFilter = jWTTokenValidatorFilter;
	}

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//	CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

		http
//		.sessionManagement(smc -> smc//.sessionFixation(sfc -> sfc.newSession())
//				.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
				.csrf(csrfConfig -> csrfConfig.disable())
				.sessionManagement(
						sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())// requiresSecure
				.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
					@Override
					public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
						CorsConfiguration config = new CorsConfiguration();
						config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
						config.setAllowedMethods(Collections.singletonList("*"));
						config.setAllowCredentials(true);
						config.setAllowedHeaders(Collections.singletonList("*"));
						config.setExposedHeaders(Arrays.asList("Authorization"));
						config.setMaxAge(3600L);
						return config;
					}
				}))
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
								"/swagger-resources/**", "/configuration/ui", "/configuration/security",
								"/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/ws/**")
						.permitAll().requestMatchers("/api/user/register", "/api/auth/login").permitAll()
						.requestMatchers("/api/**").authenticated())
				.addFilterBefore(jWTTokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
		// .csrf(csrfConfig -> csrfConfig.disable())
		;
//		http.formLogin(withDefaults());
//		http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
//		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));// asta e
		// globala
		// //GLobal
		// config
		return http.build();

//		
//		
//		
//		CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
//		
//		http
////		.sessionManagement(smc -> smc//.sessionFixation(sfc -> sfc.newSession())
////				.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true))
//		.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())//requiresSecure
//			.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
//				@Override
//				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//					CorsConfiguration config = new CorsConfiguration();
//					config.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
//					config.setAllowedMethods(Collections.singletonList("*"));
//					config.setAllowCredentials(true);
//					config.setAllowedHeaders(Collections.singletonList("*"));
//					config.setExposedHeaders(Arrays.asList("Authorization"));
//					config.setMaxAge(3600L);
//					return config;
//				}
//			}))
//			.csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
////					.ignoringRequestMatchers("/register")
//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
//			.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//			.addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//			.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
//			//			.csrf(csrfConfig -> csrfConfig.disable())
//			.authorizeHttpRequests((requests) -> requests
//			.requestMatchers("/v3/api-docs", "/v3/api-docs/**", "/swagger-resources",
//								"/swagger-resources/**", "/configuration/ui", "/configuration/security",
//								"/swagger-ui/**", "/webjars/**", "/swagger-ui.html", "/ws/**").permitAll()
//			.requestMatchers("/contact", "/error", "/api/user/register", "/invalidSession").permitAll()
//			.requestMatchers("/api/**").authenticated()
//			.requestMatchers("/api/**").hasRole("ADMIN")
//			.requestMatchers("/api/**").hasAnyRole("ADMIN","USER"));
//		http.formLogin(withDefaults());
//		http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//		http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
////		http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));// asta e
//		// globala
//		// //GLobal
//		// config
//		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
