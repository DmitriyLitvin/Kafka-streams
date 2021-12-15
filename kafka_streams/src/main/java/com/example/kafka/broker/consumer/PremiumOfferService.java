package com.example.kafka.broker.consumer;

import com.example.kafka.broker.message.PremiumOfferMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PremiumOfferService {

    @KafkaListener(id ="premium-offer-one", topics = "t.commodity.offer-one", containerFactory = "premiumOfferKafkaListenerContainerFactory")
    public void premiumOfferOne(@Payload PremiumOfferMessage premiumOfferMessage){
        System.out.println(premiumOfferMessage);
    }

    @KafkaListener(id ="premium-offer-two", topics = "t.commodity.offer-two", containerFactory = "premiumOfferKafkaListenerContainerFactory")
    public void premiumOfferTwo(@Payload PremiumOfferMessage premiumOfferMessage){
        System.out.println(premiumOfferMessage);
    }

    @KafkaListener(id ="premium-offer-three", topics = "t.commodity.offer-three", containerFactory = "premiumOfferKafkaListenerContainerFactory")
    public void premiumOfferThree(@Payload PremiumOfferMessage premiumOfferMessage){
        System.out.println(premiumOfferMessage);
    }
}
