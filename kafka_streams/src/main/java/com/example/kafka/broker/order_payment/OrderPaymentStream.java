package com.example.kafka.broker.order_payment;

import com.example.kafka.broker.message.OnlineOrderMessage;
import com.example.kafka.broker.message.OnlineOrderPaymentMessage;
import com.example.kafka.broker.message.OnlinePaymentMessage;
import com.example.kafka.util.OnlineOrderTimestampExtractor;
import com.example.kafka.util.OnlinePaymentTimestampExtractor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
public class OrderPaymentStream {

    //@Bean
    public KStream<String, OnlineOrderMessage> kStreamOrderPayment(StreamsBuilder streamsBuilder) {
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        var paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        var orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        var orderStream = streamsBuilder.stream("t.commodity.online-order",
                Consumed.with(stringSerde, orderSerde, new OnlineOrderTimestampExtractor(), null));
        var paymentStream = streamsBuilder.stream("t.commodity.online-payment",
                Consumed.with(stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));


        orderStream.join(paymentStream, this::joinerOrderPayment, JoinWindows.of(Duration.ofHours(1)),
                StreamJoined.with(stringSerde, orderSerde, paymentSerde))
                .through("t.commodity.join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde)).print(Printed.toSysOut());


        return orderStream;
    }

    private OnlineOrderPaymentMessage joinerOrderPayment(OnlineOrderMessage orderMessage,
                                                         OnlinePaymentMessage paymentMessage) {
        var orderPaymentMessage = new OnlineOrderPaymentMessage();
        orderPaymentMessage.setOnlineOrderNumber(paymentMessage.getOnlineOrderNumber());
        orderPaymentMessage.setOrderDateTime(orderMessage.getOrderDateTime());
        orderPaymentMessage.setPaymentDateTime(paymentMessage.getPaymentDateTime());
        orderPaymentMessage.setPaymentMethod(paymentMessage.getPaymentMethod());
        orderPaymentMessage.setPaymentNumber(paymentMessage.getPaymentNumber());
        orderPaymentMessage.setTotalAmount(orderMessage.getTotalAmount());
        orderPaymentMessage.setUsername(orderMessage.getUsername());

        return orderPaymentMessage;
    }
}
