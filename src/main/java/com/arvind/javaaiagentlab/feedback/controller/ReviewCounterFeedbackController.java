package com.arvind.javaaiagentlab.feedback.controller;

import com.arvind.javaaiagentlab.feedback.dto.ReviewCounterFeedbackRequest;
import com.arvind.javaaiagentlab.feedback.entity.ReviewCounterFeedback;
import com.arvind.javaaiagentlab.feedback.repository.ReviewCounterFeedbackRepository;
import com.arvind.javaaiagentlab.feedback.service.ReviewCounterFeedbackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/feedback/counter-feedback")
public class ReviewCounterFeedbackController {

    private final ReviewCounterFeedbackService service;
    private final ReviewCounterFeedbackRepository repository;
    private final String ingestionToken;

    public ReviewCounterFeedbackController(
            ReviewCounterFeedbackService service,
            ReviewCounterFeedbackRepository repository,
            @Value("${feedback.ingestion.token:}") String ingestionToken) {
        this.service = service;
        this.repository = repository;
        this.ingestionToken = ingestionToken;
    }

    @PostMapping
    public ResponseEntity<?> ingest(
            @RequestHeader(value = "X-Feedback-Token", required = false)
            String token,
            @RequestBody ReviewCounterFeedbackRequest request) {

        if (!isAuthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        if (request.githubCommentId() == null
                || request.aiReviewCommentId() == null
                || request.repository() == null
                || request.prNumber() == null
                || request.author() == null
                || request.commentBody() == null
                || request.createdAt() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Missing required fields"));
        }

        boolean inserted = service.save(request);

        return ResponseEntity.ok(Map.of(
                "status", inserted ? "inserted" : "already_exists"
        ));
    }

    @GetMapping("/pr/{prNumber}")
    public ResponseEntity<?> getByPr(
            @RequestHeader(value = "X-Feedback-Token", required = false)
            String token,
            @PathVariable Integer prNumber,
            @RequestParam String repo) {

        if (!isAuthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        List<ReviewCounterFeedback> result =
                repository.findByRepositoryAndPrNumberOrderByCreatedAtAsc(
                        repo, prNumber);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/finding/{aiReviewCommentId}")
    public ResponseEntity<?> getByFinding(
            @RequestHeader(value = "X-Feedback-Token", required = false)
            String token,
            @PathVariable Long aiReviewCommentId) {

        if (!isAuthorized(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Unauthorized"));
        }

        return ResponseEntity.ok(
                repository.findByAiReviewCommentIdOrderByCreatedAtAsc(
                        aiReviewCommentId));
    }

    private boolean isAuthorized(String token) {
        if (ingestionToken == null || ingestionToken.isBlank()
                || token == null || token.isBlank()) {
            return false;
        }

        return MessageDigest.isEqual(
                ingestionToken.getBytes(StandardCharsets.UTF_8),
                token.getBytes(StandardCharsets.UTF_8));
    }
}
