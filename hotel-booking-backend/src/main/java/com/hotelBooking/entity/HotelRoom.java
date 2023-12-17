package com.hotelBooking.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class HotelRoom {
	@Id
	@GeneratedValue
	private Long id;
	private int no_of_pepole;
	private double price;
	private Date booking_start_date;
	private Date booking_end_date;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HotelRoom() {
		
	}
	@Override
	public String toString() {
		return "HotelRoom [no_of_pepole=" + no_of_pepole + ", price=" + price
				+ ", booking_start_date=" + booking_start_date + ", booking_end_date=" + booking_end_date + "]";
	}
	
	public int getNo_of_pepole() {
		return no_of_pepole;
	}
	public void setNo_of_pepole(int no_of_pepole) {
		this.no_of_pepole = no_of_pepole;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Date getBooking_start_date() {
		return booking_start_date;
	}
	public void setBooking_start_date(Date booking_start_date) {
		this.booking_start_date = booking_start_date;
	}
	public Date getBooking_end_date() {
		return booking_end_date;
	}
	public void setBooking_end_date(Date booking_end_date) {
		this.booking_end_date = booking_end_date;
	}
	public HotelRoom(int no_of_pepole, double price, Date booking_start_date,
			Date booking_end_date) {
		super();
	
		this.no_of_pepole = no_of_pepole;
		this.price = price;
		this.booking_start_date = booking_start_date;
		this.booking_end_date = booking_end_date;
	}
	
	
}
