package com.example.kafka.broker.inventory;

import com.example.kafka.broker.message.InventoryMessage;
import com.example.kafka.config.InventoryTimestamp;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.processor.TopicNameExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

import static org.apache.kafka.streams.kstream.Printed.toSysOut;

@Configuration
public class InventoryThreeStream {

    //@Bean
    public KStream<String, InventoryMessage> kStreamInventory(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var longSerde = Serdes.Long();
        var inventoryMessageSerde = new JsonSerde<>(InventoryMessage.class);
        var inventoryTimestampExtractor = new InventoryTimestamp();
        var windowLength = Duration.ofHours(1);
        var hopLength = Duration.ofMinutes(20);
        var windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());

        var inventoryStream = builder
                .stream("t.commodity.inventory", Consumed.with(stringSerde, inventoryMessageSerde, inventoryTimestampExtractor, null));

        var streamInventoryMessage = inventoryStream
                .mapValues(v -> v.getType().equals("ADD") ?  v.getQuantity() : -v.getQuantity())
                .groupByKey()
                .windowedBy(TimeWindows.of(windowLength).advanceBy(hopLength))
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream();

        streamInventoryMessage.through("t.commodity.inventory-three", Produced.with(windowSerde, longSerde))
                .print(Printed.<Windowed<String>, Long>toSysOut());

        return inventoryStream;
    }
}
