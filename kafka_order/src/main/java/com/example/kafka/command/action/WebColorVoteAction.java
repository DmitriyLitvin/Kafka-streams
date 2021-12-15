package com.example.kafka.command.action;

import com.example.kafka.api.request.WebColorVoteRequest;
import com.example.kafka.broker.message.WebColorVoteMessage;
import com.example.kafka.broker.producer.WebColorVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WebColorVoteAction {

	@Autowired
	private WebColorVoteProducer producer;

	public void publishToKafka(WebColorVoteRequest request) {
		var message = new WebColorVoteMessage();

		message.setUsername(request.getUsername());
		message.setColor(request.getColor());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
