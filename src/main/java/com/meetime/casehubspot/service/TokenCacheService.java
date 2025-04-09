package com.meetime.casehubspot.service;

import com.meetime.casehubspot.dto.TokenResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class TokenCacheService {

    private String accessToken;
    private long expirationTimeMillis;

    public void storeToken(TokenResponseDTO token) {
        this.accessToken = token.getAccess_token();
        this.expirationTimeMillis = System.currentTimeMillis() + (token.getExpires_in() * 1000L) - 60000;
    }

    public String getToken() {
        if (isTokenValid()) {
            return accessToken;
        }
        throw new IllegalStateException("Token expirado ou não encontrado.");
    }

    public boolean isTokenValid() {
        return accessToken != null && System.currentTimeMillis() < expirationTimeMillis;
    }

    public void clearToken() {
        this.accessToken = null;
        this.expirationTimeMillis = 0;
    }
}
