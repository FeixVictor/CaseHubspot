package com.meetime.casehubspot.controller;

import com.meetime.casehubspot.service.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService service;

    @GetMapping("/authorize")
    public String authorize() {
        return service.generateAuthorizationUrl();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam String code) {
        return service.exchangeCodeForToken(code);
    }
}