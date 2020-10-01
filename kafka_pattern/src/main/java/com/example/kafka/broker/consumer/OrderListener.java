package com.example.kafka.broker.consumer;

import com.example.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


@Service
public class OrderListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderListener.class);

	@KafkaListener(groupId = "order", topics = "t.commodity.order")
	public void listen(@Payload OrderMessage message) {
		var totalItemAmount = message.getPrice() * message.getQuantity();

		LOG.info("Processing order {}, item {}, credit card number {}. Total amount for this item is {}",
				message.getOrderNumber(), message.getItemName(), message.getCreditCardNumber(), totalItemAmount);
	}

}
