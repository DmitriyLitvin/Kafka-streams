package com.example.kafka.broker.producer;

import com.example.kafka.broker.message.WebColorVoteMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteProducer {

	@Autowired
	private KafkaTemplate<String, WebColorVoteMessage> kafkaTemplate;

	public void publish(WebColorVoteMessage message) {
		kafkaTemplate.send("t.commodity.web.vote-color", message.getUsername(), message);
	}

}
