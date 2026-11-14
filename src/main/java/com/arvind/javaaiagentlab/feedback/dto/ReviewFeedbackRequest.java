package com.arvind.javaaiagentlab.feedback.dto;


import java.time.Instant;

public record ReviewFeedbackRequest(
        Long reactionId,
        String repository,
        Integer prNumber,
        Long commentId,
        String filePath,
        Integer lineNumber,
        String reaction,
        String reactedBy,
        Instant reactionCreatedAt,
        String commentBody
) {
}
