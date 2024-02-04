package com.hotelBooking.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hotelBooking.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	
	
	Logger logger=LoggerFactory.getLogger(JwtAuthenticationFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException{
		
		logger.info("inside doFilterinternal "+request.getHeaders(ALREADY_FILTERED_SUFFIX));
		final String authHeader=request.getHeader("Authorization");
		String jwt="";
		String userEmail="";
		if(authHeader==null || !authHeader.startsWith("Bearer ")) {
			logger.info("calling basic authentication ");
		    filterChain.doFilter(request, response);
		    logger.info("completed basic authentication");
		    return;
		}
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            jwt = authHeader.substring(7);
	            userEmail = jwtService.extractUsername(jwt);
	        }

		if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(userEmail);
			if(jwtService.isTokenValid(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities());
			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			
			SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			
			
		}
		filterChain.doFilter(request, response);
	}

    

}
