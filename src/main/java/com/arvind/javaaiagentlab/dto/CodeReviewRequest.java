
package com.arvind.javaaiagentlab.dto;

public record CodeReviewRequest(
        String fileName,
        String language,
        String code
) {}
