package com.example.kafka.broker.producer;

import com.example.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class OrderProducer {

	private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

	@Autowired
	private KafkaTemplate<String, OrderMessage> kafkaTemplate;

//	private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
//		int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;
//
//		List<Header> headers = new ArrayList<>();
//		var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());
//		headers.add(surpriseBonusHeader);
//
//		return new ProducerRecord<String, OrderMessage>("t.commodity.order", null, message.getOrderNumber(), message,
//				headers);
//	}

	public void publish(OrderMessage message) {
		//var producerRecord = buildProducerRecord(message);

		kafkaTemplate.send("t.commodity.order", message);

		LOG.info("Just a dummy messsage for order {}, item {} published successfuly", message.getOrderNumber(),
				message.getItemName());
	}

}
