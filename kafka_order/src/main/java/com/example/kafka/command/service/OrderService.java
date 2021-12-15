package com.example.kafka.command.service;

import com.example.kafka.api.request.OrderRequest;
import com.example.kafka.command.action.OrderAction;
import com.example.kafka.entity.Order;
import com.example.kafka.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class OrderService {

	@Autowired
	private OrderAction action;

	public String saveOrder(OrderRequest request) {
		// 1. convert OrderRequest to Order
		Order order = action.convertToOrder(request);

		// 2. save Order to database
		action.saveToDatabase(order);

		// 3. flatten the item & order as kafka message, and publish
		OrderAction orderAction = action;
		for (OrderItem orderItem : order.getItems()) {
			orderAction.publishToKafka(orderItem);
		}

		// 4. return order number (auto generated)
		return order.getOrderNumber();
	}

}
