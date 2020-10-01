package com.example.kafka.config;

import com.example.kafka.broker.message.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;


    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "client");
        return props;
    }

    @Bean
    public ProducerFactory<String, OrderMessage> producerOrderMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, OrderMessage> kafkaOrderMessageTemplate() {
        KafkaTemplate<String, OrderMessage> template = new KafkaTemplate<>(producerOrderMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, PromotionMessage> producerPromotionMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, PromotionMessage> kafkaTemplate() {
        KafkaTemplate<String, PromotionMessage> template = new KafkaTemplate<>(producerPromotionMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, DiscountMessage> producerDiscountMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, DiscountMessage> discountMessageTemplate() {
        KafkaTemplate<String, DiscountMessage> template = new KafkaTemplate<>(producerDiscountMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, FeedbackMessage> producerFeedbackMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, FeedbackMessage> discountFeedbackTemplate() {
        KafkaTemplate<String, FeedbackMessage> template = new KafkaTemplate<>(producerFeedbackMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, FlashSaleVoteMessage> flashSaleVoteMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, FlashSaleVoteMessage> flashSaleVoteMessageTemplate() {
        KafkaTemplate<String, FlashSaleVoteMessage> template = new KafkaTemplate<>(flashSaleVoteMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, InventoryMessage> inventoryMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, InventoryMessage> inventoryMessageTemplate() {
        KafkaTemplate<String, InventoryMessage> template = new KafkaTemplate<>(inventoryMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, OnlineOrderMessage> onlineOrderMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, OnlineOrderMessage> onlineOrderMessageTemplate() {
        KafkaTemplate<String, OnlineOrderMessage> template = new KafkaTemplate<>(onlineOrderMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @Bean
    public ProducerFactory<String, OnlinePaymentMessage> onlinePaymentMessageFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, OnlinePaymentMessage> onlinePaymentMessageTemplate() {
        KafkaTemplate<String, OnlinePaymentMessage> template = new KafkaTemplate<>(onlinePaymentMessageFactory());
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }


}