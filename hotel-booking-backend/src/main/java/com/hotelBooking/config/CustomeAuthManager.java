package com.hotelBooking.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomeAuthManager implements AuthenticationManager {
	
	@Autowired
	private CustomAuthProvider customAuthProvider;
	
	Logger logger=LoggerFactory.getLogger(CustomeAuthManager.class);

	@Override
	public Authentication authenticate(Authentication authentication) {
		// TODO Auto-generated method stub
		Authentication auth = null;
		try {
			logger.info("inside custom manager ");
			auth= customAuthProvider.authenticate(authentication);
			logger.info("customManager completed authobject value "+auth.isAuthenticated());
		}catch(Exception ex)
		{
			logger.info("exception mssage"+ex.getMessage());
		}
		return auth;
	}

}
