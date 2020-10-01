package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
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

@Configuration
public class FeedbackRatingTwoStream {

    //@Bean
    public KStream<String, FeedbackMessage> kStreamFeedBackRating(StreamsBuilder streamsBuilder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        var feedBackRatingOneSerde = new JsonSerde<>(FeedbackRatingTwoMessage.class);
        var feedBackRatingOneStoreValueSerde = new JsonSerde<>(FeedbackRatingTwoStoreValue.class);

        var feedBackStream = streamsBuilder
                .stream("t.commodity.feedback", Consumed.with(stringSerde, feedbackSerde));
        var feedBackStateStoreName = "feedbackRatingTwoStateStore";
        var storeSupplier = Stores.inMemoryKeyValueStore(feedBackStateStoreName);
        var storeBuilder = Stores.keyValueStoreBuilder(
                storeSupplier, stringSerde, feedBackRatingOneStoreValueSerde);
        streamsBuilder.addStateStore(storeBuilder);

        feedBackStream.transformValues(() ->
                new FeedbackRatingTwoTransformer(feedBackStateStoreName),feedBackStateStoreName)
        .through("t.commodity.feedback-detailed-rating", Produced.with(stringSerde, feedBackRatingOneSerde))
                .print(Printed.<String, FeedbackRatingTwoMessage>toSysOut());

        return feedBackStream;
    }
}
