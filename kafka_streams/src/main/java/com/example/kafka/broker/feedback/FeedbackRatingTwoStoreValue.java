package com.example.kafka.broker.feedback;

import java.util.Map;
import java.util.TreeMap;

public class FeedbackRatingTwoStoreValue {
    Map<Integer, Long> integerLongMap = new TreeMap<>();

    public Map<Integer, Long> getIntegerLongMap() {
        return integerLongMap;
    }

    public void setIntegerLongMap(Map<Integer, Long> integerLongMap) {
        this.integerLongMap = integerLongMap;
    }

    @Override
    public String toString() {
        return "FeedbackRatingTwoStoreValue{" +
                "integerLongMap=" + integerLongMap +
                '}';
    }
}
