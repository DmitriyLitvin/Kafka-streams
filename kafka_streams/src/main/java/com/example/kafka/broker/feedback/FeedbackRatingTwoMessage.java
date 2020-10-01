package com.example.kafka.broker.feedback;

import java.util.Map;
import java.util.TreeMap;

public class FeedbackRatingTwoMessage {
    private String location;
    private double avarageRating;
    private Map<Integer, Long> integerLongMap = new TreeMap<>();

    public Map<Integer, Long> getIntegerLongMap() {
        return integerLongMap;
    }

    public void setIntegerLongMap(Map<Integer, Long> integerLongMap) {
        this.integerLongMap = integerLongMap;
    }

    public FeedbackRatingTwoMessage() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAvarageRating() {
        return avarageRating;
    }

    public void setAvarageRating(double avarageRating) {
        this.avarageRating = avarageRating;
    }

    @Override
    public String toString() {
        return "FeedbackRatingTwoMessage{" +
                "location='" + location + '\'' +
                ", avarageRating=" + avarageRating +
                ", integerLongMap=" + integerLongMap +
                '}';
    }
}
