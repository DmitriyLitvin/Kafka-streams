package com.example.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

	@Bean
	public NewTopic topicFlashVoteUser() {
		return TopicBuilder.name("t.commodity.flashsale.vote-user-item").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicFlashVoteOne() {
		return TopicBuilder.name("t.commodity.flashsale.vote-one-item").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicInventoryTotal() {
		return TopicBuilder.name("t.commodity.inventory-total-one").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic topicInventoryThree() {
		return TopicBuilder.name("t.commodity.inventory-three").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic onlineOrderPayment() {
		return TopicBuilder.name("t.commodity.join-order-payment-one").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic onlineOrderPaymentTwo() {
		return TopicBuilder.name("t.commodity.join-order-payment-two").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic onlineOrderPaymentThree() {
		return TopicBuilder.name("t.commodity.join-order-payment-three").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteOneUsernameColor() {
		return TopicBuilder.name("t.commodity.web.vote-one-username-color").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteOneUsernameLayout() {
		return TopicBuilder.name("t.commodity.web.vote-one-username-layout").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteOneStream() {
		return TopicBuilder.name("t.commodity.web.vote-one-stream").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteTwoUsernameColor() {
		return TopicBuilder.name("t.commodity.web.vote-two-username-color").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteTwoUsernameLayout() {
		return TopicBuilder.name("t.commodity.web.vote-two-username-layout").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteTwoStream() {
		return TopicBuilder.name("t.commodity.web.vote-two-stream").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteThreeUsernameColor() {
		return TopicBuilder.name("t.commodity.web.vote-three-username-color").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteThreeUsernameLayout() {
		return TopicBuilder.name("t.commodity.web.vote-three-username-layout").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic voteThree() {
		return TopicBuilder.name("t.commodity.web.vote-three-stream").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic offerOne() {
		return TopicBuilder.name("t.commodity.offer-one").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic offerTwo() {
		return TopicBuilder.name("t.commodity.offer-two").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic offerThree() {
		return TopicBuilder.name("t.commodity.offer-three").partitions(1).replicas(1).build();
	}

	@Bean
	public NewTopic premiumOfferFiltered() {
		return TopicBuilder.name("t.commodity.premium.user.filtered").partitions(1).replicas(1).build();
	}
}
