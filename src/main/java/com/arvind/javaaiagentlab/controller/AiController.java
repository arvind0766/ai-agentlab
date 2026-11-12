
package com.arvind.javaaiagentlab.controller;

import com.arvind.javaaiagentlab.service.AiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AiController {

    private final AiService aiService;

    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/ask")
    public String ask(@RequestBody String question) {
        return aiService.ask(question);
    }
}
