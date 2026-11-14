package com.arvind.javaaiagentlab.feedback.service;

import com.arvind.javaaiagentlab.feedback.dto.ReviewCounterFeedbackRequest;
import com.arvind.javaaiagentlab.feedback.entity.ReviewCounterFeedback;
import com.arvind.javaaiagentlab.feedback.repository.ReviewCounterFeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewCounterFeedbackService {

    private final ReviewCounterFeedbackRepository repository;

    public ReviewCounterFeedbackService(
            ReviewCounterFeedbackRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean save(ReviewCounterFeedbackRequest request) {

        if (repository.existsByGithubCommentId(request.githubCommentId())) {
            return false;
        }

        ReviewCounterFeedback feedback = new ReviewCounterFeedback();

        feedback.setGithubCommentId(request.githubCommentId());
        feedback.setAiReviewCommentId(request.aiReviewCommentId());
        feedback.setRepository(request.repository());
        feedback.setPrNumber(request.prNumber());
        feedback.setAuthor(request.author());
        feedback.setCommentType(normalizeCommentType(request.commentType()));
        feedback.setCommentBody(request.commentBody());
        feedback.setCreatedAt(request.createdAt());

        repository.save(feedback);

        return true;
    }

    private String normalizeCommentType(String commentType) {
        if (commentType == null || commentType.isBlank()) {
            return "OTHER";
        }

        return switch (commentType.toUpperCase()) {
            case "QUESTION", "DISAGREEMENT", "EXPLANATION" ->
                    commentType.toUpperCase();
            default -> "OTHER";
        };
    }
}