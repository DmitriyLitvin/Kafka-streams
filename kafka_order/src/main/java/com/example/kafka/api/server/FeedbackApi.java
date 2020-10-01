package com.example.kafka.api.server;

import com.example.kafka.api.request.FeedbackRequest;
import com.example.kafka.command.service.FeedbackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/feedback")
@Slf4j
public class FeedbackApi {

	@Autowired
	private FeedbackService service;

	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> create(@RequestBody FeedbackRequest request) {
		service.createFeedback(request);
		return ResponseEntity.status(HttpStatus.CREATED).body("Thanks for your feedback");
	}
}
