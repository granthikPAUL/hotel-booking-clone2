package com.hotelBooking.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.hotelBooking.entity.User;

@Repository
public interface UserRepository extends Neo4jRepository<User,Long>{

}
