package com.meetime.casehubspot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "hubspot")
public class HubSpotConfiguration {
	private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String url;
    private String scope;
}
