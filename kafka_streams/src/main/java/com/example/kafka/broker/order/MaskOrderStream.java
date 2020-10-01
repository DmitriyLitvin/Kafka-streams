package com.example.kafka.broker.order;

import com.example.kafka.broker.message.OrderMessage;
import com.example.kafka.broker.serde.OrderSerde;

import com.example.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MaskOrderStream {

    //@Bean
    public KStream<String, OrderMessage> kStreamCommodityTrading(StreamsBuilder streamsBuilder) {
        var stringSerde = Serdes.String();
        var jsonSerde = new OrderSerde();
        KStream<String, OrderMessage> maskedStream = streamsBuilder
                .stream("t.commodity.order", Consumed.with(stringSerde, jsonSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);
        maskedStream.to("t.commodity.order-masked", Produced.with(stringSerde, jsonSerde));

        maskedStream.print(Printed.<String, OrderMessage>toSysOut().withLabel("masked stream"));

        return maskedStream;
    }
}
