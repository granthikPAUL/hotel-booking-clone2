package com.hotelBooking.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class TransactionDetails {

	@Id
	private String order_id;
	private Double order_amount;
	private String currency;
	@Override
	public String toString() {
		return "TransactonDetails [order_id=" + order_id + ", order_amount=" + order_amount + ", currency=" + currency
				+ "]";
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public Double getOrder_amount() {
		return order_amount;
	}
	public void setOrder_amount(Double order_amount) {
		this.order_amount = order_amount;
	}
	public String getCurrency() {
		return currency;
	}
	public TransactionDetails() {
		
	}
	public TransactionDetails(String order_id, Double order_amount, String currency) {
		super();
		this.order_id = order_id;
		this.order_amount = order_amount;
		this.currency = currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
