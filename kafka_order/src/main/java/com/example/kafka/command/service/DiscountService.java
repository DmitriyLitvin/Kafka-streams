package com.example.kafka.command.service;

import com.example.kafka.api.request.DiscountRequest;
import com.example.kafka.command.action.DiscountAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DiscountService {

	@Autowired
	private DiscountAction action;

	public void createDiscount(DiscountRequest request) {
		action.publishToKafka(request);
	}

}
