package com.sreyah.skybookai.controller;

import com.sreyah.skybookai.dto.AIRequestDTO;
import com.sreyah.skybookai.dto.AIResponseDTO;
import com.sreyah.skybookai.service.AIAssistantService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController
{
    @Autowired
    private AIAssistantService aiAssistantService;

    @PostMapping("/chat")
    public ResponseEntity<AIResponseDTO> chat(
            @Valid @RequestBody AIRequestDTO request)
    {
        return ResponseEntity.ok(
                aiAssistantService.processMessage(
                        request.getMessage()
                )
        );
    }
}