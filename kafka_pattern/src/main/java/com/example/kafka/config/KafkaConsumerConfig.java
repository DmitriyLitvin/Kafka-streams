package com.example.kafka.config;

import com.example.kafka.broker.message.OrderMessage;
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

//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//    @Value(value = "${kafka.server}")
//    private String kafkaServer;
//
//    @Value(value = "${spring.kafka.consumer.group.id}")
//    private String groupId;
//
//    private KafkaProperties kafkaProperties = new KafkaProperties();
//
//    public Map<String, Object> props() {
//        Map<String, Object> props = kafkaProperties.buildConsumerProperties();
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                kafkaServer);
//        props.put(
//                ConsumerConfig.GROUP_ID_CONFIG,
//                groupId);
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class);
//        props.put(
//                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                JsonDeserializer.class);
//        return props;
//    }
//
//    @Bean
//    public ConsumerFactory<String, OrderMessage> orderConsumerFactory() {
//        return new DefaultKafkaConsumerFactory(
//                props(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(OrderMessage.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, OrderMessage>
//    orderKafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, OrderMessage> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(orderConsumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, PromotionMessage> promotionConsumerFactory() {
//        return new DefaultKafkaConsumerFactory(
//                props(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(PromotionMessage.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, PromotionMessage>
//    promotionKafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, PromotionMessage> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(promotionConsumerFactory());
//        return factory;
//    }
//
//    @Bean
//    public ConsumerFactory<String, DiscountMessage> discountConsumerFactory() {
//        return new DefaultKafkaConsumerFactory(
//                props(),
//                new StringDeserializer(),
//                new JsonDeserializer<>(DiscountMessage.class));
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, DiscountMessage>
//    discountKafkaListenerContainerFactory() {
//
//        ConcurrentKafkaListenerContainerFactory<String, DiscountMessage> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(discountConsumerFactory());
//        return factory;
//    }
//}

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
    public ConsumerFactory<String, OrderMessage> consumerOrderMessageFactory() {
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

