package com.example.kafka.config;

import com.example.kafka.broker.message.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${kafka.server}")
    private String kafkaServer;


    @Bean
    public KafkaListenerContainerFactory<?> batchOrderMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOrderMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleOrderMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOrderMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchDiscountMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DiscountMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerDiscountMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleDiscountMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DiscountMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerDiscountMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchPromotionMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PromotionMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerPromotionMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singlePromotionMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PromotionMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerPromotionMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFeedBackMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FeedbackMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFeedbackMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFeedBackMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String,FeedbackMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFeedbackMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchFlashSaleVoteMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FlashSaleVoteMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFlashSaleVoteMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleFlashSaleVoteMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FlashSaleVoteMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFlashSaleVoteMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchInventoryMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerInventoryMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleInventoryMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, InventoryMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerInventoryMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchPaymentMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OnlinePaymentMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOnlinePaymentMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singlePaymentMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OnlinePaymentMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOnlinePaymentMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchOnlineOrderMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OnlineOrderMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOnlineOrderMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleOnlineOrderMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OnlineOrderMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerOnlineOrderMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, OrderMessage> consumerOrderMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, DiscountMessage> consumerDiscountMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, PromotionMessage> consumerPromotionMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, FeedbackMessage> consumerFeedbackMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, FlashSaleVoteMessage> consumerFlashSaleVoteMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, InventoryMessage> consumerInventoryMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, OnlinePaymentMessage> consumerOnlinePaymentMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConsumerFactory<String, OnlineOrderMessage> consumerOnlineOrderMessageFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }

    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}

