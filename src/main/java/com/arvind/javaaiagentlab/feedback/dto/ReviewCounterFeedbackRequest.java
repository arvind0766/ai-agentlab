package com.arvind.javaaiagentlab.feedback.dto;


import java.time.Instant;

public record ReviewCounterFeedbackRequest(
        Long githubCommentId,
        Long aiReviewCommentId,
        String repository,
        Integer prNumber,
        String author,
        String commentType,
        String commentBody,
        Instant createdAt
) {
}