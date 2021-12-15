package com.example.kafka.command.service;

import com.example.kafka.api.request.WebColorVoteRequest;
import com.example.kafka.command.action.WebColorVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebColorVoteService {

    @Autowired
    private WebColorVoteAction action;

    public void createColorVote(WebColorVoteRequest request) {
        action.publishToKafka(request);
    }

}