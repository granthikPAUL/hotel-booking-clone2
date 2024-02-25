package com.hotelBooking.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class WebSecurityConfig {

	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private CustomeAuthManager customeAuthManager;
	

	@Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.httpBasic().and()
		.csrf().disable().cors((cors)->cors.configurationSource(apiConfigurationSource()))
		.authorizeHttpRequests()
		.requestMatchers("/saveUser").permitAll()
		.requestMatchers("/hotel/findAll").permitAll()
		.anyRequest().authenticated()
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.authenticationManager(customeAuthManager)
		.addFilterBefore(jwtAuthenticationFilter,BasicAuthenticationFilter.class);
		return http.build();
	 }
	@Bean
	CorsConfigurationSource apiConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowCredentials(true);
		configuration.setAllowedOrigins(Collections.singletonList("http://localhost:5173"));
		configuration.setAllowedHeaders(Collections.singletonList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
