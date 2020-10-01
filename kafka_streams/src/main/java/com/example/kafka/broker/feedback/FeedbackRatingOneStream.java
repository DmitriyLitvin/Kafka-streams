package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
import lombok.Builder;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import static org.apache.kafka.streams.kstream.Printed.toSysOut;

@Configuration
public class FeedbackRatingOneStream {

    //@Bean
    public KStream<String, FeedbackMessage> kStreamFeedBackRating(StreamsBuilder streamsBuilder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        var feedBackRatingOneSerde = new JsonSerde<>(FeedbackRatingOneMessage.class);
        var feedBackRatingOneStoreValueSerde = new JsonSerde<>(FeedbackRatingOneStoreValue.class);

        var feedBackStream = streamsBuilder
                .stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));
        var feedBackStateStoreName = "feedbackRatingOneStateStore";
        var storeSupplier = Stores.inMemoryKeyValueStore(feedBackStateStoreName);
        var storeBuilder = Stores.keyValueStoreBuilder(
                storeSupplier, stringSerde, feedBackRatingOneStoreValueSerde);
        streamsBuilder.addStateStore(storeBuilder);

        feedBackStream.transformValues(() ->
                new FeedbackRatingOneTransformer(feedBackStateStoreName),feedBackStateStoreName)
        .through("t.commodity.feedback-rating", Produced.with(stringSerde, feedBackRatingOneSerde))
                .print(Printed.<String, FeedbackRatingOneMessage>toSysOut());

        return feedBackStream;
    }
}
