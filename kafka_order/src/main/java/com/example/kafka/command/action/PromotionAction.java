package com.example.kafka.command.action;

import com.example.kafka.api.request.PromotionRequest;
import com.example.kafka.broker.message.PromotionMessage;
import com.example.kafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PromotionAction {

	@Autowired
	private PromotionProducer producer;

	public void publishToKafka(PromotionRequest request) {
		var message = new PromotionMessage(request.getPromotionCode());

		producer.publish(message);
	}

}
