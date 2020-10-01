package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Map;
import java.util.Optional;

public class FeedbackRatingTwoTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingTwoMessage> {

    private ProcessorContext processorContext;
    private String stateStoreName;
    private KeyValueStore<String, FeedbackRatingTwoStoreValue> ratingStateStore;

    public FeedbackRatingTwoTransformer(String stateStoreName) {
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingTwoStoreValue>) this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingTwoMessage transform(FeedbackMessage feedbackMessage) {
        var storeValue =
                Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                        .orElse(new FeedbackRatingTwoStoreValue());
        Map<Integer, Long> ratingMap = storeValue.getIntegerLongMap();
        if (ratingMap.get(feedbackMessage.getRating()) == null) {
            ratingMap.put(feedbackMessage.getRating(), 1l);
        } else {
            ratingMap.put(feedbackMessage.getRating(), ratingMap.get(feedbackMessage.getRating()) + 1);
        }
        storeValue.setIntegerLongMap(ratingMap);
        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        long sum = 0;
        long newCountRating = 0;
        for (Map.Entry<Integer, Long> pair: storeValue.getIntegerLongMap().entrySet()) {
            sum += pair.getKey() * pair.getValue();
            newCountRating += pair.getValue();
        }

        var branchRating = new FeedbackRatingTwoMessage();
        branchRating.setLocation(feedbackMessage.getLocation());
        double avgRating = Math.round((double) sum / newCountRating * 10d) / 10d;
        branchRating.setAvarageRating(avgRating);
        branchRating.setIntegerLongMap(ratingMap);

        return branchRating;
    }


    @Override
    public void close() {

    }
}
