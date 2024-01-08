package com.hotelBooking.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.data.annotation.Persistent;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import com.fasterxml.jackson.annotation.ObjectIdGenerators.UUIDGenerator;

@Node
public class Hotel {

	@Id
	@GeneratedValue
	private Long hotel_id;
	private String hotel_name;
	private String hotel_city;
	private String hotel_address;
	private int total_rooms;
	private double rating;
	
	public Hotel() {
		
	}

	public Hotel(Long hotel_id, String hotel_name, String hotel_address, int total_rooms, String amenities_provided,String hotel_city,double rating ) {
		super();
		this.hotel_id = hotel_id;
		this.hotel_name = hotel_name;
		this.hotel_address = hotel_address;
		this.total_rooms = total_rooms;
		this.amenities_provided = amenities_provided;
		this.hotel_city=hotel_city;
		this.rating=rating;
	}
	public String getHotel_city() {
		return hotel_city;
	}

	public void setHotel_city(String hotel_city) {
		this.hotel_city = hotel_city;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "Hotel [hotel_id=" + hotel_id + ", hotel_name=" + hotel_name + ", hotel_city=" + hotel_city
				+ ", hotel_address=" + hotel_address + ", total_rooms=" + total_rooms + ", rating=" + rating
				+ ", amenities_provided=" + amenities_provided + "]";
	}

	public Hotel(String hotel_name, String hotel_address, int total_rooms, String amenities_provided,String hotel_city,double rating ) {
		super();
	
		this.hotel_name = hotel_name;
		this.hotel_address = hotel_address;
		this.total_rooms = total_rooms;
		this.amenities_provided = amenities_provided;
		this.hotel_city=hotel_city;
		this.rating=rating;
	}
	public Long getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(Long hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String getHotel_name() {
		return hotel_name;
	}
	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}
	public String getHotel_address() {
		return hotel_address;
	}
	public void setHotel_address(String hotel_address) {
		this.hotel_address = hotel_address;
	}
	public int getTotal_rooms() {
		return total_rooms;
	}
	public void setTotal_rooms(int total_rooms) {
		this.total_rooms = total_rooms;
	}
	public String getAmenities_provided() {
		return amenities_provided;
	}
	public void setAmenities_provided(String amenities_provided) {
		this.amenities_provided = amenities_provided;
	}
	private String amenities_provided;
	
	@Relationship(type = "rooms_in_hotel")
	private List<HotelRoom> hotelRooms;

	public List<HotelRoom> getHotelRooms() {
		return hotelRooms;
	}

	public void setHotelRooms(List<HotelRoom> hotelRooms) {
		this.hotelRooms = hotelRooms;
	}
	
}
