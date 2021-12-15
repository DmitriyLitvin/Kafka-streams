package com.example.kafka.broker.consumer;

import com.example.kafka.broker.message.OrderReplyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderReplyListener {

	private static final Logger LOG = LoggerFactory.getLogger(OrderReplyListener.class);

	@KafkaListener(groupId = "order-reply",topics = "t.commodity.order-reply", containerFactory = "orderReplyKafkaListenerContainerFactory")
	public void listen(OrderReplyMessage message) {
		LOG.info("Reply message received : {}", message);
	}

}
