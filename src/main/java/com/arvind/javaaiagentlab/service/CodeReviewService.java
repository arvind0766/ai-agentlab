
package com.arvind.javaaiagentlab.service;

import com.arvind.javaaiagentlab.dto.CodeReviewRequest;
import com.arvind.javaaiagentlab.dto.CodeReviewResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class  CodeReviewService {

    private final ChatClient chatClient;

    public CodeReviewService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public CodeReviewResponse review(CodeReviewRequest request) {

        return chatClient.prompt()
                .system("""
                        You are reviewing a GitHub Pull Request unified diff.
                        
                        For every finding, return:
                        - severity
                        - category
                        - filePath
                        - lineNumber
                        - title
                        - description
                        - suggestedFix
                        - confidence
                        
                        IMPORTANT RULES FOR INLINE REVIEW:
                        
                        1. filePath must exactly match the file path in the diff.
                        2. lineNumber must be the actual line number in the NEW version
                           of the file (the right-hand side of the diff).
                        3. Only report findings on added or modified lines.
                        4. Do not report findings on deleted lines.
                        5. Do not invent file paths or line numbers.
                        6. If you cannot confidently map a finding to an added or
                           modified line, do not return that finding.
                        7. The unified diff contains hunk headers such as:
                           @@ -10,4 +10,6 @@
                           Use the new-side line numbers to map findings.
                        8. Be conservative. Prefer fewer high-confidence findings.
                        """)
                .user("""
                        Review the following code.

                        File: %s
                        Language: %s

                        <code_to_review>
                        %s
                        </code_to_review>
                        """.formatted(
                        request.fileName(),
                        request.language(),
                        request.code()
                ))
                .call()
                .entity(CodeReviewResponse.class);
    }
}
