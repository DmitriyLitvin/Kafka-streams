package com.example.kafka.broker.premium;

import com.example.kafka.broker.message.PremiumOfferMessage;
import com.example.kafka.broker.message.PremiumPurchaseMessage;
import com.example.kafka.broker.message.PremiumUserMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PremiumOfferThreeStreams {


    @Bean
    public KStream<String, PremiumOfferMessage> kStreamPremiumOffer(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var purchaseSerde = new JsonSerde<>(PremiumPurchaseMessage.class);
        var userSerde = new JsonSerde<>(PremiumUserMessage.class);
        var offerSerde = new JsonSerde<>(PremiumOfferMessage.class);

        var purchaseStream =  builder.stream("t.commodity.premium-purchase", Consumed.with(stringSerde, purchaseSerde))
                .selectKey((k,v) -> v.getUsername());

        var filterLevel = List.of("gold", "diamond");
        builder.stream("t.commodity.premium-user", Consumed.with(stringSerde, userSerde))
                .filter((k,v) -> filterLevel.contains(v.getLevel().toLowerCase()))
                .to("t.commodity.premium.user.filtered", Produced.with(stringSerde, userSerde));

        var userTable = builder.globalTable("t.commodity.premium.user.filtered", Consumed.with(stringSerde, userSerde));


        var offerStream = purchaseStream.join(userTable, (key, value) -> key, this::offerJoiner);

        offerStream.to("t.commodity.offer-three", Produced.with(stringSerde,offerSerde));

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
