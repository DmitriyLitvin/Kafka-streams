package com.example.kafka.command.service;

import com.example.kafka.api.request.OnlineOrderRequest;
import com.example.kafka.command.action.OnlineOrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineOrderService {

	@Autowired
	private OnlineOrderAction action;

	public void saveOnlineOrder(OnlineOrderRequest request) {
		action.publishToKafka(request);
	}

}
