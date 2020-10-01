package com.example.kafka.broker.serde;

import com.example.kafka.broker.message.DiscountMessage;

public class DiscountSerde extends CustomJsonSerde<DiscountMessage> {

    public DiscountSerde() {
        super(new CustomJsonSerializer<DiscountMessage>(),
                new CustomJsonDeserializer<DiscountMessage>(DiscountMessage.class));
    }

}

