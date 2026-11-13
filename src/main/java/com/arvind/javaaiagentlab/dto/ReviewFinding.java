package com.arvind.javaaiagentlab.dto;

public record ReviewFinding(
        String severity,
        String category,
        String filePath,
        Integer lineNumber,
        String title,
        String description,
        String suggestedFix,
        Double confidence
) {
}