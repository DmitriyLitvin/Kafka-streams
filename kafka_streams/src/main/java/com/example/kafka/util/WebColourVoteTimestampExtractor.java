package com.example.kafka.util;

import com.example.kafka.broker.message.OnlineOrderMessage;
import com.example.kafka.broker.message.WebColorVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;


public class WebColourVoteTimestampExtractor implements TimestampExtractor {

	@Override
	public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
		var message = (WebColorVoteMessage) record.value();

		return message != null ? LocalDateTimeUtil.toEpochTimestamp(message.getVoteDateTime())
				: record.timestamp();
	}

}
