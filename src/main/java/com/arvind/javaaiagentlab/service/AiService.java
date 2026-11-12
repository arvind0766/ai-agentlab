package com.arvind.javaaiagentlab.service;

import com.arvind.javaaiagentlab.dto.IncidentContext;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AiService {

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private OrderTools orderTools;

    @Autowired
    private IncidentTools incidentTools;

    public String ask(String question) {
        return chatClient
                .prompt()
                .tools(orderTools)
                .system("You are a senior Java backend engineer.")
                .user(question)
                .call()
                .content();
    }
}
