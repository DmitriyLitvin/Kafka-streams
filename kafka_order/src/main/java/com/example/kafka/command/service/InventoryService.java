package com.example.kafka.command.service;

import com.example.kafka.api.request.InventoryRequest;
import com.example.kafka.command.action.InventoryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class InventoryService {

	@Autowired
	private InventoryAction action;

	public void addInventory(InventoryRequest request) {
		action.publishToKafka(request, "ADD");
	}

	public void subtractInventory(InventoryRequest request) {
		action.publishToKafka(request, "REMOVE");
	}

}
