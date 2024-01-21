package com.hotelBooking.service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.neo4j.driver.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
	
	ObjectMapper mapper = JsonMapper.builder()
		    .addModule(new JavaTimeModule())
		    .build();

	
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
		String query="optional MATCH (n:Hotel) where id(n)="+id+"\r\n"
				+ "call{\r\n"
				+ "    with n\r\n"
				+ "    optional match(n)-[r:has_room]->(m:HotelRoom)\r\n"
				+ "    return case when r is null then null\r\n"
				+ "    else m end as rooms\r\n"
				+ "}\r\n"
				+ "return n,collect(rooms) as rooms";
		Result res= tx.run(query);
		logger.info("query string "+query);
		List<Record>records=res.list();
		Hotel hotel=mapper.convertValue(records.get(0).get("n").asMap(),Hotel.class);
		hotel.setHotel_id(records.get(0).get("n").asEntity().id());

		logger.info("hotel details "+hotel);
		
		int sizeOfRooms=records.get(0).get("rooms").asList().size();
		List<HotelRoom>listOfRooms = new ArrayList<>();
		for(int i=0;i<sizeOfRooms;i++) {
			Record record=records.get(0);		
			logger.info("room  value "+record.get("rooms").get(i).asMap());
			Map<String,Object>resultMap=record.get("rooms").get(i).asMap();
			HotelRoom hotel_room=new HotelRoom();
			hotel_room.setId(record.get("rooms").get(i).asEntity().id());
			hotel_room.setBooking_start_date(resultMap.get("booking_start_date").toString());
			hotel_room.setBooking_end_date(resultMap.get("booking_end_date").toString());
			hotel_room.setNo_of_pepole(Integer.parseInt(resultMap.get("no_of_pepole").toString()));
			hotel_room.setPrice(Double.parseDouble(resultMap.get("price").toString()));
			
			logger.info("room details"+hotel_room);
			listOfRooms.add(hotel_room);
			
		}
		hotel.setHotelRooms(listOfRooms);
//		Hotel hotel=new Hotel();
//		ObjectMapper mapper=new ObjectMapper();
//		Map<String,Object>resultMap=rec.get("n").asMap();
//		//custom object mapper
//		Hotel hotel=mapper.convertValue( rec.get("n").asMap(),Hotel.class);
//		hotel.setHotel_id(id);
//		hotel.setHotelRooms(listOfRooms);
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
	

	public String addRoomToHotel(Long roomId,Long hotelId) {
		//1.find room and hotel
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
//		HotelRoom hotelRoom= roomService.findHotelRoomById(roomId);
//		logger.info("room details "+hotelRoom);
//		Hotel hotel=findHotelById(hotelId);
//		logger.info("hotel details "+hotel);
		String query="match(n:Hotel)where id(n)="+hotelId+" match(m:HotelRoom)where id(m)="+roomId+" create (n)-[r:has_room]->(m) return r,m";
		logger.info("query "+query);
		Result res=tx.run(query);
		
		for(Record record:res.list()) {
			logger.info("relationship record  "+record.get("r").asMap());

			logger.info("room record "+record.get("m").asMap());
		}
		
		
		tx.commit();
		session.close();
		return "room added successfully";
		
		
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
	
	public List<Hotel> searchHotels(int no_of_pepole,String booking_start_date,String booking_end_date,String hotel_city,int total_rooms ){
		logger.info("calling search method"+no_of_pepole+" "+booking_end_date+" "+booking_end_date+" "+hotel_city+" "+total_rooms);
		logger.info("start date  and endDate "+booking_end_date.length()+" "+booking_start_date.length());
		Session session=neo4jDriver.session();
		Transaction tx=session.beginTransaction();
		DateTimeFormatter formatter= DateTimeFormatter.ISO_DATE;
		LocalDateTime startDate,endDate;
		if(booking_start_date.length()==0 && booking_end_date.length()==0)
		{
			booking_start_date=LocalDate.now().toString();
			booking_end_date=LocalDate.now().plusDays(1).toString();
			logger.info("no date given "+booking_start_date+' '+booking_end_date);
			
		}
		
		endDate=LocalDate.parse(booking_end_date, formatter).atStartOfDay();
		startDate=LocalDate.parse(booking_start_date, formatter).atStartOfDay();
		String query="optional match(n:Hotel) where n.hotel_city='"+hotel_city+"' and n.total_rooms>="+total_rooms+"\r\n"
				+ "with n optional match(n)-[r:has_room]->(m:HotelRoom)where m.no_of_pepole>="+no_of_pepole+" and m.booking_start_date > date('"+endDate+"') and m.booking_end_date < date('"+startDate+"') return distinct(n)";
	
		logger.info("query string "+query);
		Result res=tx.run(query);
		List<Record>records=res.list();
		logger.info("records "+records);
		List<Hotel>listOfHotel=new ArrayList<>();
		logger.info("record print "+records.get(0).get("n").isNull());
		if(!records.get(0).get("n").isNull())
		{
			for(Record record:records) {
				Hotel hotel=mapper.convertValue(record.get("n").asMap(),Hotel.class);
				hotel.setHotel_id(record.get("n").asEntity().id());
				listOfHotel.add(hotel);
			}
		}
		return listOfHotel;
	
	}

}
