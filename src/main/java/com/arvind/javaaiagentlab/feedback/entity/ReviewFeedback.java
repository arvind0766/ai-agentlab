package com.arvind.javaaiagentlab.feedback.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(
        name = "review_feedback",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_review_feedback_reaction_id",
                        columnNames = "reaction_id"
                )
        },
        indexes = {
                @Index(name = "idx_feedback_pr", columnList = "repository, pr_number"),
                @Index(name = "idx_feedback_comment", columnList = "comment_id")
        }
)
public class ReviewFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reaction_id", nullable = false, unique = true)
    private Long reactionId;

    @Column(name = "repository", nullable = false, length = 255)
    private String repository;

    @Column(name = "pr_number", nullable = false)
    private Integer prNumber;

    @Column(name = "comment_id", nullable = false)
    private Long commentId;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Column(name = "reaction", nullable = false, length = 30)
    private String reaction;

    @Column(name = "reacted_by", length = 255)
    private String reactedBy;

    @Column(name = "reaction_created_at")
    private Instant reactionCreatedAt;

    @Column(name = "comment_body", columnDefinition = "TEXT")
    private String commentBody;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public ReviewFeedback() {
    }

    public Long getId() {
        return id;
    }

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getReaction() {
        return reaction;
    }

    public void setReaction(String reaction) {
        this.reaction = reaction;
    }

    public String getReactedBy() {
        return reactedBy;
    }

    public void setReactedBy(String reactedBy) {
        this.reactedBy = reactedBy;
    }

    public Instant getReactionCreatedAt() {
        return reactionCreatedAt;
    }

    public void setReactionCreatedAt(Instant reactionCreatedAt) {
        this.reactionCreatedAt = reactionCreatedAt;
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
}
