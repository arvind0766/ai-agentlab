package com.arvind.javaaiagentlab.feedback.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "review_counter_feedback",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_counter_feedback_comment_id",
                        columnNames = "github_comment_id"
                )
        },
        indexes = {
                @Index(
                        name = "idx_counter_feedback_finding",
                        columnList = "ai_review_comment_id"
                ),
                @Index(
                        name = "idx_counter_feedback_pr",
                        columnList = "repository, pr_number"
                )
        }
)
public class ReviewCounterFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_comment_id", nullable = false, unique = true)
    private Long githubCommentId;

    @Column(name = "ai_review_comment_id", nullable = false)
    private Long aiReviewCommentId;

    @Column(name = "repository", nullable = false, length = 255)
    private String repository;

    @Column(name = "pr_number", nullable = false)
    private Integer prNumber;

    @Column(name = "author", nullable = false, length = 255)
    private String author;

    @Column(name = "comment_type", nullable = false, length = 50)
    private String commentType;

    @Column(name = "comment_body", nullable = false, columnDefinition = "TEXT")
    private String commentBody;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public ReviewCounterFeedback() {
    }

    public Long getId() {
        return id;
    }

    public Long getGithubCommentId() {
        return githubCommentId;
    }

    public void setGithubCommentId(Long githubCommentId) {
        this.githubCommentId = githubCommentId;
    }

    public Long getAiReviewCommentId() {
        return aiReviewCommentId;
    }

    public void setAiReviewCommentId(Long aiReviewCommentId) {
        this.aiReviewCommentId = aiReviewCommentId;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public Integer getPrNumber() {
        return prNumber;
    }

    public void setPrNumber(Integer prNumber) {
        this.prNumber = prNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCommentType() {
        return commentType;
    }

    public void setCommentType(String commentType) {
        this.commentType = commentType;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

