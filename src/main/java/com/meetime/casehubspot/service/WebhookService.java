package com.meetime.casehubspot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.meetime.casehubspot.dto.WebHookEvent;

@Service
public class WebhookService {

    public void process(List<WebHookEvent> events) {
        for (WebHookEvent event : events) {
            if ("contact.creation".equals(event.getSubscriptionType())) {
                System.out.println("Novo contato criado: " + event);
            }
        }
    }
}