package com.meetime.casehubspot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.meetime.casehubspot.dto.ContactDTO;
import com.meetime.casehubspot.exception.HubSpotRequestException;
import com.meetime.casehubspot.exception.TokenNotFoundException;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@Service
public class ContactService {

    @Autowired
    private TokenCacheService tokenCacheService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hubspot.url}")
    private String hubSpotApiUrl;

    @RateLimiter(name = "hubspotRateLimiter")
    public void createContact(ContactDTO contact) {
        String token = tokenCacheService.getToken();

        if (token == null || token.isBlank()) {
            throw new TokenNotFoundException("Token de acesso não encontrado ou inválido.");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
            "properties", Map.of(
                "email", contact.getEmail(),
                "firstname", contact.getFirstName(),
                "lastname", contact.getLastName(),
                "phone", contact.getPhone()
            )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                hubSpotApiUrl + "/crm/v3/objects/contacts",
                request,
                String.class
            );

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new HubSpotRequestException("Erro ao criar contato: " + response.getBody());
            }
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new HubSpotRequestException("Token expirado ou inválido. Erro de autenticação.", ex);
        } catch (HttpClientErrorException ex) {
            throw new HubSpotRequestException("Erro HTTP ao chamar HubSpot: " + ex.getStatusCode() + " - " + ex.getResponseBodyAsString(), ex);
        } catch (RestClientException ex) {
            throw new HubSpotRequestException("Erro de comunicação com a API do HubSpot.", ex);
        } catch (Exception ex) {
            throw new HubSpotRequestException("Erro inesperado ao criar contato no HubSpot.", ex);
        }
    }
}
