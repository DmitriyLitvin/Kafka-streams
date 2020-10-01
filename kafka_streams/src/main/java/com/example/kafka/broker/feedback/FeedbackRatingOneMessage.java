package com.example.kafka.broker.feedback;

public class FeedbackRatingOneMessage {
    private String location;
    private double avarageRating;

    public FeedbackRatingOneMessage() {

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
        return "FeedbackRatingOneMessage{" +
                "location='" + location + '\'' +
                ", avarageRating=" + avarageRating +
                '}';
    }
}
