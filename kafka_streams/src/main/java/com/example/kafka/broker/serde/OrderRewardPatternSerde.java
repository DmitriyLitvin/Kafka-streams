package com.example.kafka.broker.serde;

import com.example.kafka.broker.message.OrderPatternMessage;

public class OrderRewardPatternSerde extends CustomJsonSerde<OrderPatternMessage> {

    public OrderRewardPatternSerde() {
        super(new CustomJsonSerializer<OrderPatternMessage>(),
                new CustomJsonDeserializer<OrderPatternMessage>(OrderPatternMessage.class));
    }

}

