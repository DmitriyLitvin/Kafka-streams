package com.example.kafka.broker.feedback;

import com.example.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackRatingOneTransformer implements ValueTransformer<FeedbackMessage, FeedbackRatingOneMessage> {

    private ProcessorContext processorContext;
    private String stateStoreName;
    private KeyValueStore<String, FeedbackRatingOneStoreValue> ratingStateStore;

    public FeedbackRatingOneTransformer(String stateStoreName) {
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingOneStoreValue>) this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingOneMessage transform(FeedbackMessage feedbackMessage) {
        var storeValue =
                Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                        .orElse(new FeedbackRatingOneStoreValue());
        var newSumRating = storeValue.getSumRating() + feedbackMessage.getRating();
        storeValue.setSumRating(newSumRating);
        var newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        var branchRating = new FeedbackRatingOneMessage();
        branchRating.setLocation(feedbackMessage.getLocation());
        double avgRating = Math.round((double) newSumRating / newCountRating * 10d) / 10d;
        branchRating.setAvarageRating(avgRating);

        return branchRating;
    }


    @Override
    public void close() {

    }
}
