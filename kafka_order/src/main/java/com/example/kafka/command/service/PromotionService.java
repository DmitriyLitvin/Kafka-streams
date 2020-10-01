package com.example.kafka.command.service;

import com.example.kafka.api.request.PromotionRequest;
import com.example.kafka.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

	@Autowired
	private PromotionAction action;

	public void createPromotion(PromotionRequest request) {
		action.publishToKafka(request);
	}

}
