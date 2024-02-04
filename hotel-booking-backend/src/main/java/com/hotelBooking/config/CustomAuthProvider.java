package com.hotelBooking.config;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.hotelBooking.service.UserDetailsServiceImpl;
@Component
public class CustomAuthProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger logger=LoggerFactory.getLogger(CustomAuthProvider.class);
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		logger.info("inside custome auth provider ");
		// TODO Auto-generated method stub
		String emailId=authentication.getName();
		String password_entered=authentication.getCredentials().toString();
		logger.info("authentication objects inside authProvider "+emailId+" "+password_entered);
		UserDetails userDetails=userDetailsServiceImpl.loadUserByUsername(emailId);
		if(userDetails==null)
		{
			logger.info("authentication failed no user found");
			throw new AuthenticationException("user not found") {};
		}
		if (!passwordEncoder.matches(password_entered,"$2a$10$B9XeBmJt4Mv73qo.sk/2cu6d0D5K4UQ51WTgT1UKE.g12Nl/JY8kS")) {
			logger.info("authentication failed");
            throw new AuthenticationException("Invalid credentials") {};
        }
        // Create a fully authenticated Authentication object
        Authentication authenticated = new UsernamePasswordAuthenticationToken(
                userDetails, password_entered,null);
        logger.info("after authentication succesfull auth object "+authenticated.isAuthenticated());
        return authenticated;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
