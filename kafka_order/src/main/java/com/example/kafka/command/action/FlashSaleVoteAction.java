package com.example.kafka.command.action;

import com.example.kafka.api.request.FlashSaleVoteRequest;
import com.example.kafka.broker.message.FlashSaleVoteMessage;
import com.example.kafka.broker.producer.FlashSaleVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlashSaleVoteAction {

	@Autowired
	private FlashSaleVoteProducer producer;

	public void publishToKafka(FlashSaleVoteRequest request) {
		var message = new FlashSaleVoteMessage();

		message.setCustomerId(request.getCustomerId());
		message.setItemName(request.getItemName());

		producer.publish(message);
	}

}
