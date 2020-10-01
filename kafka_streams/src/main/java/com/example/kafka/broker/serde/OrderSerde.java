package com.example.kafka.broker.serde;

import com.example.kafka.broker.message.OrderMessage;

public class OrderSerde extends CustomJsonSerde<OrderMessage> {

    public OrderSerde() {
        super(new CustomJsonSerializer<OrderMessage>(),
                new CustomJsonDeserializer<OrderMessage>(OrderMessage.class));
    }

}
