package com.example.kafka.command.service;

import com.example.kafka.api.request.OnlinePaymentRequest;
import com.example.kafka.command.action.OnlinePaymentAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlinePaymentService {

	@Autowired
	private OnlinePaymentAction action;

	public void pay(OnlinePaymentRequest request) {
		action.publishPaymentToKafka(request);
	}

}
