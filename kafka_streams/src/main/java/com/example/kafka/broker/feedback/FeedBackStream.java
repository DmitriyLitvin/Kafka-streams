package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FeedBackStream {
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");

    //@Bean
    public KStream<String, String> kstreamFeedBack(StreamsBuilder builder) {
        var string = Serdes.String();
        var feedBackSerde = new JsonSerde<>(FeedbackMessage.class);


        KStream<String, String> goodFeedbackStream = builder
                .stream("t.commodity.feedback", Consumed.with(string, feedBackSerde))
                .flatMap((k, v) -> Arrays.asList(v.getFeedback().toLowerCase().split("\\s+")).stream()
                                .filter(word -> GOOD_WORDS.contains(word))
                                .distinct().map(goodWord -> KeyValue.pair(v.getLocation(), goodWord))
                                .collect(Collectors.toList()));

        goodFeedbackStream.print(Printed.<String, String>toSysOut().withLabel("good feed"));
        goodFeedbackStream.to("t.commodity.feedback.good-word", Produced.with(string, string));

        return goodFeedbackStream;

    }

    private ValueMapper<FeedbackMessage, Iterable<String>> mappedGoodWords() {
        return feedbackMessage -> Arrays.asList(feedbackMessage
                .getFeedback().toLowerCase().split("\\s+")).stream().filter(word -> GOOD_WORDS.contains(word))
                .distinct().collect(Collectors.toList());

    }
}
