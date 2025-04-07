package com.meetime.casehubspot.service;

import com.meetime.casehubspot.config.HubSpotConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class OAuthService {

    @Autowired
    private HubSpotConfiguration properties;

    private final WebClient webClient = WebClient.create();

    public String generateAuthorizationUrl() {
        return "https://app.hubspot.com/oauth/authorize" +
                "?client_id=" + properties.getClientId() +
                "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), StandardCharsets.UTF_8) +
                "&scope=" + properties.getScope() +
                "&response_type=code";
    }

    public String exchangeCodeForToken(String code) {
        return webClient.post()
                .uri("https://api.hubapi.com/oauth/v1/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("grant_type=authorization_code&client_id=" + properties.getClientId() +
                           "&client_secret=" + properties.getClientSecret() +
                           "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), StandardCharsets.UTF_8) +
                           "&code=" + code)
                .retrieve()
                .bodyToMono(Map.class)
                .block()
                .get("access_token").toString();
    }
}
