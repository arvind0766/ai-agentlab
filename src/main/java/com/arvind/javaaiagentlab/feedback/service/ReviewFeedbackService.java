package com.arvind.javaaiagentlab.feedback.service;

import com.arvind.javaaiagentlab.feedback.dto.ReviewFeedbackRequest;
import com.arvind.javaaiagentlab.feedback.entity.ReviewFeedback;
import com.arvind.javaaiagentlab.feedback.repository.ReviewFeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewFeedbackService {

    private final ReviewFeedbackRepository repository;

    public ReviewFeedbackService(ReviewFeedbackRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public boolean saveReaction(ReviewFeedbackRequest request) {

        if (repository.existsByReactionId(request.reactionId())) {
            return false;
        }

        ReviewFeedback feedback = new ReviewFeedback();

        feedback.setReactionId(request.reactionId());
        feedback.setRepository(request.repository());
        feedback.setPrNumber(request.prNumber());
        feedback.setCommentId(request.commentId());
        feedback.setFilePath(request.filePath());
        feedback.setLineNumber(request.lineNumber());
        feedback.setReaction(request.reaction());
        feedback.setReactedBy(request.reactedBy());
        feedback.setReactionCreatedAt(request.reactionCreatedAt());
        feedback.setCommentBody(request.commentBody());

        repository.save(feedback);

        return true;
    }
}
