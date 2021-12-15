package com.example.kafka.command.service;

import com.example.kafka.api.request.PremiumUserRequest;
import com.example.kafka.command.action.PremiumUserAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PremiumUserService {

	@Autowired
	private PremiumUserAction action;

	public void createUser(PremiumUserRequest request) {
		action.publishToKafka(request);
	}

}
