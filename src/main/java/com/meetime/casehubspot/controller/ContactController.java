package com.meetime.casehubspot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meetime.casehubspot.dto.ContactDTO;
import com.meetime.casehubspot.service.ContactService;

@RestController
@RequestMapping("/create-contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ContactDTO dto) {
        contactService.createContact(dto);
        return ResponseEntity.ok().build();
    }
}