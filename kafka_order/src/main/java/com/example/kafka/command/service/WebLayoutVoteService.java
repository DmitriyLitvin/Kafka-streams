package com.example.kafka.command.service;

import com.example.kafka.api.request.WebLayoutVoteRequest;
import com.example.kafka.command.action.WebLayoutVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebLayoutVoteService {

    @Autowired
    private WebLayoutVoteAction action;

    public void createLayoutVote(WebLayoutVoteRequest request) {
        action.publishToKafka(request);
    }

}
