package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic topicOrder() {
		return TopicBuilder.name("t.commodity.order").partitions(2).replicas(1).build();
	}

	@Bean
	public NewTopic topicOrderReply() {
		return TopicBuilder.name("t.commodity.order-reply").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicFeedbackRating() {
		return TopicBuilder.name("t.commodity.feedback-rating").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicFlashsale() {
		return TopicBuilder.name("t.commodity.flashsale.vote").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicInventory() {
		return TopicBuilder.name("t.commodity.inventory").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic onlineOrder() {
		return TopicBuilder.name("t.commodity.online-order").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic onlinePayment() {
		return TopicBuilder.name("t.commodity.online-payment").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteLayout() {
		return TopicBuilder.name("t.commodity.web.vote-layout").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteColor() {
		return TopicBuilder.name("t.commodity.web.vote-color").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic premiumPurchase() {
		return TopicBuilder.name("t.commodity.premium-purchase").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic premiumUser() {
		return TopicBuilder.name("t.commodity.premium-user").partitions(1).replicas(1).build();
	}

}
