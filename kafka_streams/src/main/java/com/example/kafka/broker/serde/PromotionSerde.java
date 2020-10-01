package com.example.kafka.broker.serde;


import com.example.kafka.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {

	public PromotionSerde() {
		super(new CustomJsonSerializer<PromotionMessage>(),
				new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
	}

}
