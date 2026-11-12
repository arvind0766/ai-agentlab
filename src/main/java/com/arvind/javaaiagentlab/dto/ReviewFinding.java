
package com.arvind.javaaiagentlab.dto;

public record ReviewFinding(
        String severity,
        String category,
        Integer lineNumber,
        String title,
        String description,
        String suggestedFix,
        Double confidence
) {}
