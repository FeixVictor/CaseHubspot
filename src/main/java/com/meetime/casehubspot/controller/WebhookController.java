package com.meetime.casehubspot.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetime.casehubspot.dto.WebHookEvent;
import com.meetime.casehubspot.service.WebhookService;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

	private final WebhookService webhookService;

    public WebhookController(WebhookService webhookService) {
        this.webhookService = webhookService;
    }

    @PostMapping("/contact")
    public ResponseEntity<Void> handleContactCreation(@RequestBody List<WebHookEvent> events) {
        webhookService.process(events);
        return ResponseEntity.ok().build();
    }
}
