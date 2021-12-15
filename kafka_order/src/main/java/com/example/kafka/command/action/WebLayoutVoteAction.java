package com.example.kafka.command.action;

import com.example.kafka.api.request.WebLayoutVoteRequest;
import com.example.kafka.broker.message.WebLayoutVoteMessage;
import com.example.kafka.broker.producer.WebLayoutVoteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class WebLayoutVoteAction {

	@Autowired
	private WebLayoutVoteProducer producer;

	public void publishToKafka(WebLayoutVoteRequest request) {
		var message = new WebLayoutVoteMessage();

		message.setUsername(request.getUsername());
		message.setLayout(request.getLayout());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
