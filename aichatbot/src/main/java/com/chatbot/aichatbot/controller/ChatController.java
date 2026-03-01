package com.chatbot.aichatbot.controller;

import org.springframework.web.bind.annotation.*;

import com.chatbot.aichatbot.service.OpenRouterService;


@RestController
@RequestMapping("/api/chat")
@CrossOrigin
public class ChatController {

	private final OpenRouterService service;
	
	public ChatController(OpenRouterService service) {
	    this.service = service;
	}

    @PostMapping
    public String chat(@RequestBody String message) throws Exception {
        return service.getResponse(message);
    }
}