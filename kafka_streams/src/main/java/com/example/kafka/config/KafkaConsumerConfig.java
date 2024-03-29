package com.example.kafka.config;

import com.example.kafka.broker.message.OnlineOrderMessage;
import com.example.kafka.broker.message.OnlinePaymentMessage;
import com.example.kafka.broker.message.PremiumOfferMessage;
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
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${kafka.server}")
    private String kafkaServer;

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.example.kafka.broker.message");
        return props;
    }


    @Bean
    public ConsumerFactory<String, PremiumOfferMessage> premiumOfferMessageFactory() {
        Map<String, Object> premiumOffer = consumerConfigs();
//        premiumOffer.put(ConsumerConfig.GROUP_ID_CONFIG, "premium-offer");
        return new DefaultKafkaConsumerFactory<>(premiumOffer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PremiumOfferMessage>
    premiumOfferKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PremiumOfferMessage> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(premiumOfferMessageFactory());
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> batchOrderReplyMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PremiumOfferMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(premiumOfferMessageFactory());
        factory.setBatchListener(true);
        factory.setMessageConverter(new BatchMessagingMessageConverter(converter()));
        return factory;
    }

    @Bean
    public KafkaListenerContainerFactory<?> singleOrderReplyMessageFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PremiumOfferMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(premiumOfferMessageFactory());
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    @Bean
    public StringJsonMessageConverter converter() {
        return new StringJsonMessageConverter();
    }
}
