package com.meetime.casehubspot.service;

import com.meetime.casehubspot.config.HubSpotConfiguration;
import com.meetime.casehubspot.dto.TokenResponseDTO;
import com.meetime.casehubspot.exception.OAuthException;
import com.meetime.casehubspot.exception.TokenNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class OAuthService {

    @Autowired
    private HubSpotConfiguration properties;

    @Autowired
    private TokenCacheService tokenCache;

    private final WebClient webClient = WebClient.create();

    public String generateAuthorizationUrl() {
        return "https://app.hubspot.com/oauth/authorize" +
                "?client_id=" + URLEncoder.encode(properties.getClientId(), StandardCharsets.UTF_8) +
                "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), StandardCharsets.UTF_8) +
                "&scope=" + URLEncoder.encode(properties.getScope(), StandardCharsets.UTF_8) +
                "&response_type=code";
    }

    public TokenResponseDTO exchangeCodeForToken(String code) {
        try {
        	
            TokenResponseDTO token = webClient.post()
                    .uri("https://api.hubapi.com/oauth/v1/token")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .bodyValue("grant_type=authorization_code&client_id=" + URLEncoder.encode(properties.getClientId(), StandardCharsets.UTF_8) +
                            "&client_secret=" + URLEncoder.encode(properties.getClientSecret(), StandardCharsets.UTF_8) +
                            "&redirect_uri=" + URLEncoder.encode(properties.getRedirectUri(), StandardCharsets.UTF_8) +
                            "&code=" + code)
                    .retrieve()
                    .bodyToMono(TokenResponseDTO.class)
                    .block();

            if (token == null || token.getAccess_token() == null) {
                throw new OAuthException("Resposta inválida da API do HubSpot: token nulo.");
            }

            tokenCache.storeToken(token);
            return token;

        } catch (WebClientResponseException e) {
            throw new OAuthException("Erro ao trocar código por token: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new OAuthException("Erro inesperado ao trocar código por token.", e);
        }
    }

    public String getAccessToken() {
        String token = tokenCache.getToken();
        
        if (token == null || token.isBlank()) {
            throw new TokenNotFoundException("Token de acesso não encontrado.");
        }
        
        return token;
    }
}
