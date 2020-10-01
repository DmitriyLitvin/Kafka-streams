package com.example.kafka.command.service;

import com.example.kafka.api.request.FeedbackRequest;
import com.example.kafka.command.action.FeedbackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

	@Autowired
	private FeedbackAction action;

	public void createFeedback(FeedbackRequest request) {
		action.publishToKafka(request);
	}

}
