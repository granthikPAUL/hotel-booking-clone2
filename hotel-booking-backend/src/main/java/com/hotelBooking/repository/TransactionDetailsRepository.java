package com.hotelBooking.repository;

import org.json.JSONObject;

import com.hotelBooking.entity.TransactionDetails;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

public interface TransactionDetailsRepository {

	
	public TransactionDetails createTransaction(Double amnt);
	
	public TransactionDetails OrderDetails(Order order);
}
