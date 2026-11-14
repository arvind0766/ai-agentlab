package com.arvind.javaaiagentlab.feedback.repository;


import com.arvind.javaaiagentlab.feedback.entity.ReviewFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewFeedbackRepository
        extends JpaRepository<ReviewFeedback, Long> {

    List<ReviewFeedback> findByRepositoryAndPrNumber(
            String repository,
            Integer prNumber
    );

    List<ReviewFeedback> findByCommentId(Long commentId);

    List<ReviewFeedback> findByReaction(String reaction);

    boolean existsByReactionId(Long reactionId);
}
