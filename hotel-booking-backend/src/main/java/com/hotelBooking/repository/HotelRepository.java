package com.hotelBooking.repository;

import java.util.List;
import java.util.Map;

import org.neo4j.driver.Transaction;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.hotelBooking.entity.Hotel;

public interface HotelRepository {

	
	public List<Hotel> findAllHotels();
	public Hotel findHotelById(Long id);
	public Hotel saveHotel(Hotel hotel);
	public  List<Hotel> searchHotels(int no_of_pepole,String booking_start_date,String booking_end_date,String hotel_city,int total_rooms );
	public String addRoomToHotel(Long roomId,Long hotelId);
}
