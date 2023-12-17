package com.hotelBooking.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelBooking.entity.Hotel;
import com.hotelBooking.entity.HotelRoom;
import com.hotelBooking.repository.HotelRepository;
import com.hotelBooking.repository.HotelRoomRepository;
@Service
public class HotelRoomService implements HotelRoomRepository {

	@Autowired
	Driver neo4jDriver;
	
	Logger logger=LoggerFactory.getLogger(HotelService.class);

	
	@Override
	public Map<String,Object> saveHotelRoom(HotelRoom hotelRoom) {
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String query="create(n:HotelRoom)set n.no_of_pepole=$no_of_pepole,n.price=$price,n.booking_start_date=$booking_start_date,"
				+"n.booking_end_date=$booking_end_date return n as hotelRoom,id(n) as room_id";
		
		Map<String,Object>param=new HashMap<>();
		ObjectMapper mapper=new ObjectMapper();
		param=mapper.convertValue(hotelRoom,Map.class);
		Result res= tx.run(query,param);
		List<Record>rec=res.list();
		Map<String,Object>room=new HashMap<>();
		for(Record record:rec) {
			room.put("id",record.get("room_id").asLong());
			room.put("room",record.get("hotelRoom").asNode().asMap());
		}
		tx.commit();
		session.close();
		return room;
	}

	@Override
	public HotelRoom findHotelRoomById(Long roomId) {
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String query="match(n:HotelRoom)where id(n)="+roomId+" return n as hotel_room,id(n) as id";
		logger.info(query);
		Result res=tx.run(query);
		List<Record>rec=res.list();
		HotelRoom hotel_room = new HotelRoom();
		
		for(Record record:rec) {
			logger.info(""+record.size());
			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object>resultMap=record.get("hotel_room").asMap();
			Long id=record.get("id").asLong();
			logger.info(""+resultMap);
			hotel_room=mapper.convertValue(resultMap,HotelRoom.class);
			hotel_room.setId(id);
			hotel_room.setBooking_end_date(new Date((long) resultMap.get("booking_end_date")));
			hotel_room.setBooking_start_date(new Date((long)resultMap.get("booking_start_date")));
			
//			hotel_room=mapper.convertValue(record.get(0).get("n").asNode().asMap(),HotelRoom.class);
		}
		tx.commit();
		session.close();
		return hotel_room;
	}

	public List<HotelRoom> findAllRoom() {
		
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		String query="match(n:HotelRoom)return n,id(n) as id";
		logger.info(query);
		Result res=tx.run(query);
		List<Record>rec=res.list();
		List<HotelRoom>listofRooms=new ArrayList<>();
		
		for(Record record:rec) {
//			logger.info(""+record.size());
			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object>resultMap=record.get("n").asMap();
			Long id=record.get("id").asLong();
			logger.info(""+resultMap);
			HotelRoom hotel_room=mapper.convertValue(resultMap,HotelRoom.class);
			hotel_room.setId(id);
			hotel_room.setBooking_end_date(new Date((long) resultMap.get("booking_end_date")));
			hotel_room.setBooking_start_date(new Date((long)resultMap.get("booking_start_date")));
			listofRooms.add(hotel_room);
//			hotel_room=mapper.convertValue(record.get(0).get("n").asNode().asMap(),HotelRoom.class);
		}
		tx.commit();
		session.close();
		return listofRooms;
	}

	

}
