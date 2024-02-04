package com.hotelBooking.service;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hotelBooking.entity.User;
import com.hotelBooking.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserRepository {

	Logger logger=LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	Driver neo4jDriver;
	
	ObjectMapper mapper = JsonMapper.builder()
		    .addModule(new JavaTimeModule())
		    .build();
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		/*
		 * query to find only user with email id;
		 */
		String query="optional match(n:User)where n.emailId = '"+emailId+"' return n";
		Result res=tx.run(query);
		List<Record>rec=res.list();
		logger.info("record object"+rec.get(0));
		User userDetails = null;
		if(!rec.get(0).get("n").isNull())
		{
			ObjectMapper mapper=new ObjectMapper();
			userDetails=mapper.convertValue(rec.get(0).get("n").asNode().asMap(),User.class);
		}
		logger.info("user details object fetched "+userDetails);
		tx.commit();
		session.close();
		return userDetails;
		
	}
	@Override
	public User saveUser(User user) {
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		/*
		 * query to find only user with email id;
		 */
		String query="create(n:User)set n.firstName=$firstName,n.lastName=$lastName,n.password=$password,n.emailId=$emailId return n as user";
		Map<String,Object>params=Map.of("lastName",user.getLastName(),"firstName",user.getFirstName(),"password",passwordEncoder.encode(user.getPassword()),"emailId",user.getEmailId());
		Result res=tx.run(query,params);
		List<Record>rec=res.list();
		logger.info("record object"+rec.get(0));
		User userDetails = null;
		if(!rec.get(0).get("user").isNull())
		{
			ObjectMapper mapper=new ObjectMapper();
			userDetails=mapper.convertValue(rec.get(0).get("user").asNode().asMap(),User.class);
		}
		logger.info("user created sucessfully "+userDetails);
		tx.commit();
		session.close();
		return userDetails;
	}

}
