package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FeedBackThreeStream {
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    //@Bean
    public KStream<String, String> kstreamFeedBack(StreamsBuilder builder) {
        var string = Serdes.String();
        var feedBackSerde = new JsonSerde<>(FeedbackMessage.class);

        KStream<String, String>[] feedbackStream = builder
                .stream("t.commodity.feedback", Consumed.with(string, feedBackSerde))
                .flatMap(splitWords())
                .branch(isGoodWord(), isBadWord());

        KStream<String, Long> goodFeedback = feedbackStream[0].groupBy((k, v) -> v).count().toStream();
        goodFeedback.print(Printed.<String, Long>toSysOut().withLabel("count good feed"));
        goodFeedback.to("t.commodity.feedback.good-word-count");
        KStream<String, Long> badFeedback = feedbackStream[1].groupBy((k, v) -> v).count().toStream();
        badFeedback.print(Printed.<String, Long>toSysOut().withLabel("count bad feed"));
        badFeedback.to("t.commodity.feedback.bad-word-count");


        return feedbackStream[0];

    }

    private Predicate<? super String,? super String> isBadWord() {
        return (k, v) -> BAD_WORDS.contains(v);
    }

    private Predicate<? super String, ? super String> isGoodWord() {
        return (k, v) -> GOOD_WORDS.contains(v);
    }

    private KeyValueMapper<String, FeedbackMessage, List<KeyValue<String, String>>> splitWords() {
        return (k, v) -> Arrays.asList(
                v.getFeedback().toLowerCase().split("\\s+")).stream()
                .map(w -> KeyValue.pair(v.getLocation(), w))
                .collect(Collectors.toList());

    }
}
