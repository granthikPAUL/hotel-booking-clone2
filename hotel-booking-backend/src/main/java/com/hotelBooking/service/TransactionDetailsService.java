package com.hotelBooking.service;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.hotelBooking.entity.TransactionDetails;
import com.hotelBooking.repository.TransactionDetailsRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

@Service
public class TransactionDetailsService implements TransactionDetailsRepository{

	@Autowired
	private Environment env;

	private String Currency="INR";
	
	Logger logger=LoggerFactory.getLogger(TransactionDetailsService.class);
	
	public TransactionDetails createTransaction(Double amnt)
	{
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("amount",amnt*100.00);
		jsonObject.put("currency",Currency);
		logger.info("JSON OBJECT "+jsonObject);
		try {
			String KEY=env.getProperty("KEY");
			String KEY_SECRET=env.getProperty("KEY_SECRET");
			logger.info("env values "+"key "+KEY+" secret "+KEY_SECRET);
			RazorpayClient razorpayClient=new RazorpayClient(KEY,KEY_SECRET);
			
			Order order= razorpayClient.orders.create(jsonObject);
			logger.info("order details "+order);
			return OrderDetails(order);
			
		}catch(Exception ex)
		{
			logger.info("payment exception :- " +ex.getMessage());
		}
		return null;
	}
	
	public TransactionDetails OrderDetails(Order order)
	{
		String orderId=order.get("id");
		String currency=order.get("currency");
		Double amount= Double.parseDouble(order.get("amount").toString());
		TransactionDetails transactionDetails=new TransactionDetails(orderId, amount, currency);
		return transactionDetails;
	}
	
}
