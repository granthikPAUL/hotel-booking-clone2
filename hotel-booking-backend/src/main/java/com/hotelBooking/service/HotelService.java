package com.hotelBooking.service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Records;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelBooking.entity.Hotel;
import com.hotelBooking.entity.HotelRoom;
import com.hotelBooking.repository.HotelRepository;


@Service
public class HotelService implements HotelRepository{
	
	@Autowired
	private HotelRoomService roomService;
	
	@Autowired
	Driver neo4jDriver;
	
	Logger logger=LoggerFactory.getLogger(HotelService.class);
	
	@Override
	public List<Hotel> findAllHotels() {
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		/*
		 * query to find only hotels;
		 */
		String query="match(n:Hotel)return n as hotel,id(n) as hotel_id";
		Result res=tx.run(query);
		List<Record>rec=res.list();
		logger.info(""+rec.size());
//		logger.info(rec.get(0).get("hotel").asNode().asMap().size()+"-"+rec.get(0).get("hotelRoom").asNode().asMap().size);
//		logger.info(rec.get(0).get("hotel",new Hotel())+"---"+rec.get(0).get("hotelRoom",new ArrayList<>()));
		List<Hotel>hotels = new ArrayList<>();
		for(Record record:rec) {
			ObjectMapper mapper=new ObjectMapper();
			Hotel hotel=mapper.convertValue(record.get("hotel").asNode().asMap(),Hotel.class);
			hotel.setHotel_id(record.get("hotel_id").asLong());
//			logger.info(""+record.get("hotel").asNode().get("elementId"));
		hotels.add(hotel);
//			hotel=mapper.writer(rec.get(0).get("hotel").asNode().asMap()).forType(Hotel.class);
//					rec.get(0).get("hotel").asNode().asMap();
		}
		tx.commit();
		session.close();
		return hotels;
	}

	@Override
	public Hotel findHotelById(Long id) {
		// TODO Auto-generated method stub
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String query="match(n:Hotel)where id(n)="+id+" return n";
		logger.info("---calling rooms----");
		List<HotelRoom>listOfRooms=findRoomForHotel(id);
		logger.info("list of rooms"+listOfRooms);
		Result res= tx.run(query);
		Record rec=res.list().get(0);
		ObjectMapper mapper=new ObjectMapper();
		Map<String,Object>resultMap=rec.get("n").asMap();
		//custom object mapper
		Hotel hotel=mapper.convertValue( rec.get("n").asMap(),Hotel.class);
		hotel.setHotel_id(id);
		hotel.setHotelRooms(listOfRooms);
		tx.commit();
		session.close();
		return hotel;
	}

	@Override
	public Hotel saveHotel(Hotel hotel) {
		
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String query="create(n:Hotel)set n.hotel_name=$hotel_name,n.hotel_city=$hotel_city,n.hotel_address=$hotel_address,"
				+"n.total_rooms=$total_rooms,n.rating=$rating,n.amenities_provided=$amenities_provided return n,id(n) as id";

		
		Map<String,Object>param=new HashMap<>();
		ObjectMapper mapper=new ObjectMapper();
		param=mapper.convertValue(hotel,Map.class);
		Result res= tx.run(query,param);
		List<Record>rec=res.list();
		Hotel hotelObj=new Hotel();
		for(Record record:rec) {
			Long id=record.get("id").asLong();
			Map<String,Object>resultMap=record.get("n").asMap();
			hotelObj=mapper.convertValue(resultMap,Hotel.class);
			hotelObj.setHotel_id(id);
		}

		tx.commit();
		session.close();
		return hotelObj;
		
	}
	

	public Hotel addRoomToHotel(Long roomId,Long hotelId) {
		//1.find room and hotel
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		HotelRoom hotelRoom= roomService.findHotelRoomById(roomId);
		Hotel hotel=findHotelById(hotelId);
		String query="match(n:Hotel)where id(n)="+hotelId+" match(m:HotelRoom)where id(m)="+roomId+" create (n)-[r:has_room]->(m) return r";
		logger.info(query);
		Result res=tx.run(query);
		Record rec=res.list().get(0);
		logger.info(""+rec.get("r").asMap());
//		List<HotelRoom>listOfRooms=hotel.getHotelRooms();
//		if(listOfRooms==null || listOfRooms.isEmpty())
//		{
//			listOfRooms=new ArrayList<>();
//		}
//		listOfRooms.add(hotelRoom);
//		hotel.setHotelRooms(listOfRooms);
		tx.commit();
		session.close();
		return hotel;
		
		
	}
	
	public List<HotelRoom> findRoomForHotel(Long hotelId) {
		logger.info("calling");
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String q="match(n:Hotel)where id(n)="+hotelId+" with n match(n)-[r:has_room]-(m:HotelRoom) return m,id(m)";
		Result res=tx.run(q);
		List<HotelRoom>listOfRooms=new ArrayList<>();
		List<Record>rec=res.list();
		logger.info(""+rec.size());
		ObjectMapper mapper=new ObjectMapper();
		for(Record record:rec) {
			logger.info(""+record.get("m").asMap());
			HotelRoom hotelRoom=mapper.convertValue(record.get("m").asMap(),HotelRoom.class);
//			logger.info("room object"+ hotelRoom);
			hotelRoom.setId(record.get("id(m)").asLong());
			listOfRooms.add(hotelRoom);
		}
		return  listOfRooms;
	}

}
