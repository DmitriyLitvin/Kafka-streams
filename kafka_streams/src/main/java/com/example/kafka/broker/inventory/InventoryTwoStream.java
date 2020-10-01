package com.example.kafka.broker.inventory;

import com.example.kafka.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class InventoryTwoStream {

    //@Bean
    public KStream<String, Long> kStreamInventory(StreamsBuilder builder) {
        var stringSerde  = Serdes.String();
        var longSerde = Serdes.Long();
        var inventoryMessageSerde = new JsonSerde<>(InventoryMessage.class);
        var streamInventoryMessage = builder
                .stream("t.commodity.inventory", Consumed.with(stringSerde, inventoryMessageSerde))
                .mapValues(v -> v.getType().equals("ADD") ?  v.getQuantity() : -v.getQuantity())
                .groupByKey()
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream();

        streamInventoryMessage.through("t.commodity.inventory-total-one").print(Printed.toSysOut());

        return streamInventoryMessage;
    }
}
