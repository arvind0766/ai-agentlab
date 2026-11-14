package com.arvind.javaaiagentlab.feedback.controller;


import com.arvind.javaaiagentlab.feedback.dto.ReviewFeedbackRequest;
import com.arvind.javaaiagentlab.feedback.service.ReviewFeedbackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback")
public class ReviewFeedbackController {

    private final ReviewFeedbackService feedbackService;
    private final String ingestionToken;

    public ReviewFeedbackController(
            ReviewFeedbackService feedbackService,
            @Value("${feedback.ingestion.token:}") String ingestionToken) {
        this.feedbackService = feedbackService;
        this.ingestionToken = ingestionToken;
    }

    @PostMapping("/github-reactions")
    public ResponseEntity<?> ingestReaction(
            @RequestHeader(value = "X-Feedback-Token", required = false)
            String token,
            @RequestBody ReviewFeedbackRequest request) {

        if (!isAuthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        if (request.reactionId() == null
                || request.repository() == null
                || request.prNumber() == null
                || request.commentId() == null
                || request.reaction() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing required feedback fields"));
        }

        boolean inserted = feedbackService.saveReaction(request);

        return ResponseEntity.ok(Map.of(
                "status", inserted ? "inserted" : "already_exists"
        ));
    }

    private boolean isAuthorized(String token) {
        if (ingestionToken == null || ingestionToken.isBlank()
                || token == null || token.isBlank()) {
            return false;
        }

        return MessageDigest.isEqual(
                ingestionToken.getBytes(StandardCharsets.UTF_8),
                token.getBytes(StandardCharsets.UTF_8)
        );
    }
}

