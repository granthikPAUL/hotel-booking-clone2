package com.hotelBooking.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelBooking.config.*;
import com.hotelBooking.entity.Hotel;
import com.hotelBooking.entity.HotelRoom;
import com.hotelBooking.entity.TransactionDetails;
import com.hotelBooking.entity.User;
import com.hotelBooking.repository.UserRepository;
import com.hotelBooking.service.HotelRoomService;
import com.hotelBooking.service.HotelService;
import com.hotelBooking.service.TransactionDetailsService;
import com.hotelBooking.service.UserDetailsServiceImpl;
import com.hotelBooking.util.AuthenticationObject;

import jakarta.servlet.http.HttpServletResponse;

@RestController
class HotelBookingController {
	Logger logger=LoggerFactory.getLogger(HotelBookingController.class);


	@Autowired
	private UserRepository userepo;
	@Autowired
	private HotelService hotelservice;
	@Autowired
	private HotelRoomService roomService;
	
	@Autowired
	private TransactionDetailsService paymentService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsServiceImpl userService;
	@Autowired
	private CustomeAuthManager authmanager;

	
	@PostMapping("/saveUser")
	public ResponseEntity<User> getHome(@RequestBody User user) {
		/*
		 * User user=new User(); user.setEmailId("xyz@gmai.com");
		 * user.setFirstName("abcd"); user.setLastName("paul");
		 * user.setPassword("dfsdfsdf"); userepo.save(user); return user;
		 */
		
		return new ResponseEntity<User>(userService.saveUser(user),HttpStatus.OK);
	}
	


	
	@PostMapping("/create/hotel")
	public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
			return new ResponseEntity<Hotel>(hotelservice.saveHotel(hotel),HttpStatus.OK);
	}
	@PostMapping("/create/room")
	public ResponseEntity<Map<String,Object>> saveHotelRoom(@RequestBody HotelRoom room){
	
		return new ResponseEntity<Map<String,Object>>(roomService.saveHotelRoom(room),HttpStatus.OK);
	}
	
	@GetMapping("/hotel/findAll")
	public ResponseEntity<List<Hotel>> getAllHotel(){
		return new ResponseEntity<List<Hotel>>(hotelservice.findAllHotels(),HttpStatus.OK);
	}
	
	@GetMapping("/hotel/find/{id}")
	public ResponseEntity<Hotel> findHotelById(@PathVariable Long id){
		return new ResponseEntity<Hotel>(hotelservice.findHotelById(id),HttpStatus.OK);
	}
	
	@GetMapping("/hotelRoom/findRoom/{id}")
	public ResponseEntity<HotelRoom> findHotelRoomById(@PathVariable Long id){
		return new ResponseEntity<HotelRoom>(roomService.findHotelRoomById(id),HttpStatus.OK);
	}
	@GetMapping("/hotelRoom/findAll")
	public ResponseEntity<List<HotelRoom>>findAllHotelRoom(){
		
		return new ResponseEntity<List<HotelRoom>>(roomService.findAllRoom(),HttpStatus.OK);
	}
	
	@PutMapping("/addRoomTohotel/{roomid}/{hotelId}")
	public ResponseEntity<String> addRoomToHotel(@PathVariable Long roomid,@PathVariable Long hotelId)
	{
		return new ResponseEntity<String>(hotelservice.addRoomToHotel(roomid, hotelId),HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Hotel>> searchHotels(@RequestParam(value = "adultNo",defaultValue ="1") int no_of_pepole,
			@RequestParam(value="checkIn",defaultValue ="") String booking_start_date,
			@RequestParam(value="checkOut",defaultValue="") String booking_end_date,
			@RequestParam(value="location",defaultValue = "kolkata")String hotel_city,
			@RequestParam(value="roomNo",defaultValue = "1") int total_rooms )
	{
		
		return new ResponseEntity<List<Hotel>>(hotelservice.searchHotels(no_of_pepole, booking_start_date, booking_end_date, hotel_city, total_rooms),HttpStatus.OK);
	}
	@GetMapping("/createTransaction/{amount}")
	public ResponseEntity<TransactionDetails> paymentController(@PathVariable(name = "amount") Double amnt){
		
		return new ResponseEntity<TransactionDetails>(paymentService.createTransaction(amnt),HttpStatus.OK);
	}
	
	@GetMapping("/user/authenticate")
	public String homepage2(HttpServletResponse response) {
		Authentication obj=SecurityContextHolder.getContext().getAuthentication();
		UserDetails user=userDetailsServiceImpl.loadUserByUsername(obj.getName());
		logger.info("after token "+obj.getName());
		logger.info("user details for token "+user);
		String token=jwtService.generateToken(user);
		response.setHeader("Authorization","Bearer "+token);
		System.out.println(response.getHeaderNames());
		System.out.println(token);
		return "put is working fine";
	}
}
