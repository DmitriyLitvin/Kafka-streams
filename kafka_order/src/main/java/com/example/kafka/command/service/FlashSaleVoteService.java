package com.example.kafka.command.service;

import com.example.kafka.api.request.FlashSaleVoteRequest;
import com.example.kafka.command.action.FlashSaleVoteAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class FlashSaleVoteService {

	@Autowired
	private FlashSaleVoteAction action;

	public void createFlashSaleVote(FlashSaleVoteRequest request) {
		action.publishToKafka(request);
	}

}
