package com.hotelBooking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.hotelBooking.entity.Hotel;
import com.hotelBooking.entity.HotelRoom;
import com.hotelBooking.repository.HotelRepository;
import com.hotelBooking.repository.HotelRoomRepository;
@Service
public class HotelRoomService implements HotelRoomRepository {

	@Autowired
	Driver neo4jDriver;
	
	Logger logger=LoggerFactory.getLogger(HotelService.class);
	
	
	ObjectMapper mapper = JsonMapper.builder()
		    .addModule(new JavaTimeModule())
		    .build();

	
	@Override
	public Map<String,Object> saveHotelRoom(HotelRoom hotelRoom) {
		
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
//		LocalDateTime startDate=LocalDateTime.of(hotelRoom.getBooking_start_date(),LocalTime.now());
//		logger.info("booking startDate"+startDate);
		String query="create(n:HotelRoom)set n.no_of_pepole=$no_of_pepole,n.price=$price,n.booking_start_date=date($booking_start_date),n.booking_end_date=date($booking_end_date) return n as hotelRoom,id(n) as room_id";
		
		logger.info("query"+query);
		
		Map<String,Object>param=new HashMap<>();
//		ObjectMapper mapper=new ObjectMapper();
		logger.info("converting object to map");
//		param=mapper.convertValue(hotelRoom,Map.class);
		param.put("no_of_pepole",hotelRoom.getNo_of_pepole());
		param.put("price",hotelRoom.getPrice());
		param.put("booking_start_date",hotelRoom.getBooking_start_date());
		param.put("booking_end_date",hotelRoom.getBooking_end_date());
		
		logger.info("conversion successfull");
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
//			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object>resultMap=record.get("hotel_room").asMap();
			Long id=record.get("id").asLong();
			logger.info("fetching room record"+resultMap);
//			hotel_room=mapper.convertValue(resultMap,HotelRoom.class);
			hotel_room.setId(id);
			hotel_room.setBooking_start_date(resultMap.get("booking_start_date").toString());
			hotel_room.setBooking_end_date(resultMap.get("booking_end_date").toString());
			hotel_room.setNo_of_pepole(Integer.parseInt(resultMap.get("no_of_pepole").toString()));
			hotel_room.setPrice(Double.parseDouble(resultMap.get("price").toString()));
			logger.info("room object "+hotel_room);
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
//			ObjectMapper mapper=new ObjectMapper();
			Map<String,Object>resultMap=record.get("n").asMap();
			Long id=record.get("id").asLong();
			logger.info("printing result map"+resultMap);
//			HotelRoom hotel_room=mapper.convertValue(resultMap,HotelRoom.class);
			HotelRoom hotel_room=new HotelRoom();
			hotel_room.setId(id);
			hotel_room.setBooking_start_date(resultMap.get("booking_start_date").toString());
			hotel_room.setBooking_end_date(resultMap.get("booking_end_date").toString());
			hotel_room.setNo_of_pepole(Integer.parseInt(resultMap.get("no_of_pepole").toString()));
			hotel_room.setPrice(Double.parseDouble(resultMap.get("no_of_pepole").toString()));
			logger.info("hotel room object"+hotel_room);
			try {
				logger.info("converting to string"+mapper.writeValueAsString(resultMap));
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			hotel_room.setId(id);/
//			hotel_room.setBooking_end_date(resultMap.get("booking_end_date")));
//			logger.info("booking startDate"+hotel_room.getBooking_start_date());
//			hotel_room.setBooking_start_date(new Date((long)resultMap.get("booking_start_date")));
//			logger.info("room info "+hotel_room);
			listofRooms.add(hotel_room);
//			hotel_room=mapper.convertValue(record.get(0).get("n").asNode().asMap(),HotelRoom.class);
		}
		tx.commit();
		session.close();
		return listofRooms;
	}

	

}
