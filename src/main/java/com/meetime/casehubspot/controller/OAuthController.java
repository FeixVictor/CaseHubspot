package com.meetime.casehubspot.controller;

import com.meetime.casehubspot.dto.TokenResponseDTO;
import com.meetime.casehubspot.service.OAuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService service;

    @GetMapping("/authorize")
    public RedirectView authorize() {
    	String url = service.generateAuthorizationUrl();
        return new RedirectView(url);
    }

    @GetMapping("/callback")
    public TokenResponseDTO callback(@RequestParam String code) {
        return service.exchangeCodeForToken(code);
    }
    
}