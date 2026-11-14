package com.arvind.javaaiagentlab.feedback.repository;


import com.arvind.javaaiagentlab.feedback.entity.ReviewCounterFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewCounterFeedbackRepository
        extends JpaRepository<ReviewCounterFeedback, Long> {

    List<ReviewCounterFeedback> findByRepositoryAndPrNumberOrderByCreatedAtAsc(
            String repository,
            Integer prNumber
    );

    List<ReviewCounterFeedback> findByAiReviewCommentIdOrderByCreatedAtAsc(
            Long aiReviewCommentId
    );

    boolean existsByGithubCommentId(Long githubCommentId);
}
