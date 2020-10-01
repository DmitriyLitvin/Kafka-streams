package com.example.kafka.broker.flash_sale;

import com.example.kafka.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Configuration
public class FlashSaleOneStream {

    //@Bean
    public KStream<String, String> kStreamFlashSaleVote(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);
        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 0));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));


        var flashSaleVoteStream =
                builder.stream("t.commodity.flashsale.vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                        .transformValues(() -> new FlashSaleTwoValueTransformer(voteStart, voteEnd))
                        .filter((k, v) -> v != null)
                        .map((k, v) -> KeyValue.pair(v.getCustomerId(), v.getItemName()));

        flashSaleVoteStream.to("t.commodity.flashsale.vote-user-item", Produced.with(stringSerde, stringSerde));

        builder.table("t.commodity.flashsale.vote-user-item")
                .groupBy((k, v) -> KeyValue.pair(v, v))
                .count()
                .toStream().through("t.commodity.flashsale.vote-one-item")
                .print(Printed.toSysOut());

        return flashSaleVoteStream;

    }
}
