
package com.arvind.javaaiagentlab.dto;

import java.util.List;

public record CodeReviewResponse(
        String summary,
        List<ReviewFinding> findings
) {}
