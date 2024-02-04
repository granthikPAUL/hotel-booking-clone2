package com.hotelBooking.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import com.hotelBooking.entity.User;

@Repository
public interface UserRepository extends UserDetailsService{
	
	public User saveUser(User user);

}
