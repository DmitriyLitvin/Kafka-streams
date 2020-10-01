package com.example.kafka.broker.serde;

import com.example.kafka.broker.message.OrderRewardMessage;

public class OrderRewardMessageSerde extends CustomJsonSerde<OrderRewardMessage> {

    public OrderRewardMessageSerde() {
        super(new CustomJsonSerializer<OrderRewardMessage>(),
                new CustomJsonDeserializer<OrderRewardMessage>(OrderRewardMessage.class));
    }

}

