package com.example.kafka.command.action;

import com.example.kafka.api.request.PremiumUserRequest;
import com.example.kafka.broker.message.PremiumUserMessage;
import com.example.kafka.broker.producer.PremiumUserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PremiumUserAction {

	@Autowired
	private PremiumUserProducer producer;

	public void publishToKafka(PremiumUserRequest request) {
		var message = new PremiumUserMessage();

		message.setUsername(request.getUsername());
		message.setLevel(request.getLevel());

		producer.publish(message);
	}

}
