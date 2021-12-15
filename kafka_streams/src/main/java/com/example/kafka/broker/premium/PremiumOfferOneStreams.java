package com.example.kafka.broker.premium;

import com.example.kafka.broker.message.PremiumOfferMessage;
import com.example.kafka.broker.message.PremiumPurchaseMessage;
import com.example.kafka.broker.message.PremiumUserMessage;
import lombok.Builder;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PremiumOfferOneStreams {


    //@Bean
    public KStream<String, PremiumOfferMessage> kStreamPremiumOffer(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        var purchaseStream =  builder.stream("t.commodity.premium-purchase", Consumed.with(stringSerde, purchaseSerde))
                .selectKey((k,v) -> v.getUsername());

        var filterLevel = List.of("gold", "diamond");
        var userTable = builder.table("t.commodity.premium-user", Consumed.with(stringSerde, userSerde))
                .filter((k,v) -> filterLevel.contains(v.getLevel().toLowerCase()));

        var offerStream = purchaseStream.join(userTable, this::offerJoiner, Joined.with(stringSerde, purchaseSerde, userSerde));

        offerStream.to("t.commodity.offer", Produced.with(stringSerde,offerSerde));

        return offerStream;
    }

    private PremiumOfferMessage offerJoiner(PremiumPurchaseMessage  premiumPurchaseMessage, PremiumUserMessage premiumUserMessage) {
        var result = new PremiumOfferMessage();

        result.setUsername(premiumPurchaseMessage.getUsername());
        result.setLevel(premiumUserMessage.getLevel());
        result.setPurchaseNumber(premiumPurchaseMessage.getPurchaseNumber());

        return result;
    }
}
