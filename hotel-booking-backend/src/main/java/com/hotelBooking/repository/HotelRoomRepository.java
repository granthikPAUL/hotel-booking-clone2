package com.hotelBooking.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.hotelBooking.entity.HotelRoom;
@Repository
public interface HotelRoomRepository {

	
	public Map<String,Object> saveHotelRoom(HotelRoom hotelRoom);
	
	public HotelRoom findHotelRoomById(Long hotel_id);
	
	public List<HotelRoom> findAllRoom();
}
