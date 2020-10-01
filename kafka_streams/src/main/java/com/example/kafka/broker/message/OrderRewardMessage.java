package com.example.kafka.broker.message;


import com.example.kafka.util.LocalDateTimeDeserializer;
import com.example.kafka.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

public class OrderRewardMessage {

	private String itemName;

	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	private LocalDateTime orderDateTime;

	private String orderLocation;

	private String orderNumber;

	private int price;

	private int quantity;

	public String getItemName() {
		return itemName;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public String getOrderLocation() {
		return orderLocation;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public int getPrice() {
		return price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public void setOrderLocation(String orderLocation) {
		this.orderLocation = orderLocation;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "OrderMessage [orderLocation=" + orderLocation + ", orderNumber=" + orderNumber + ", orderDateTime=" + orderDateTime + ", itemName=" + itemName + ", price=" + price
				+ ", quantity=" + quantity + "]";
	}
}
