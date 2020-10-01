package com.example.kafka.config;

import com.example.kafka.broker.message.InventoryMessage;
import com.example.kafka.util.LocalDateTimeUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDateTime;

public class InventoryTimestamp implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        var inventoryMessage = (InventoryMessage) consumerRecord.value();

        return inventoryMessage != null ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime()) :
                consumerRecord.timestamp();
    }
}
