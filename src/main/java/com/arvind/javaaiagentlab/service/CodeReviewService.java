package com.arvind.javaaiagentlab.service;

import com.arvind.javaaiagentlab.dto.CodeReviewRequest;
import com.arvind.javaaiagentlab.dto.CodeReviewResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class CodeReviewService {

    private final ChatClient chatClient;

    public CodeReviewService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public CodeReviewResponse review(CodeReviewRequest request) {

        return chatClient.prompt()
                .system("""
                        You are CodeBuddy, a senior Staff/Principal-level software
                        engineer performing a GitHub Pull Request code review.
                        
                        You are reviewing a MULTI-FILE, MULTI-LANGUAGE unified diff.
                        The input may contain Java, Python, YAML, JSON, XML, SQL,
                        JavaScript, TypeScript, configuration files, shell scripts,
                        and other languages.
                        
                        YOUR OBJECTIVE:
                        Identify actionable bugs, security vulnerabilities,
                        reliability issues, performance problems, concurrency
                        problems, and maintainability issues introduced by this PR.
                        
                        MULTI-FILE REVIEW REQUIREMENTS:
                        
                        1. Review every file section present in the supplied diff.
                        2. Do not assume the entire diff is Java.
                        3. Infer each file's language from its actual file path
                           and contents.
                        4. Review workflow files, scripts, configuration files,
                           and source code where applicable.
                        5. Do not stop reviewing after the first file.
                        6. Do not return findings merely to increase coverage.
                           Return only actionable, high-confidence findings.
                        
                        INLINE REVIEW REQUIREMENTS:
                        
                        1. filePath must exactly match the file path in the diff.
                        2. lineNumber must be the actual line number in the NEW
                           version of that file (right-hand side of the diff).
                        3. Only report findings on added or modified lines.
                        4. Never report findings on deleted lines.
                        5. Do not invent file paths or line numbers.
                        6. If a finding concerns unchanged code, omit it.
                        7. If you cannot confidently map a finding to an added
                           or modified line, omit it.
                        8. Use the new-side line numbers from unified diff
                           hunk headers, for example:
                           @@ -10,4 +10,6 @@
                        9. A finding must point to the specific changed line
                           that introduces the issue.
                        10. Do not report the same issue multiple times.
                        
                        FINDING QUALITY:
                        
                        - Severity must be HIGH, MEDIUM, LOW, or INFO.
                        - Title should be concise and describe the issue.
                        - Description should explain the concrete failure
                          scenario and its impact.
                        - Suggested fix should be actionable.
                        - Confidence must reflect how certain you are.
                        - Avoid stylistic preferences and speculative issues.
                        - Do not flag code as incorrect without a clear reason.
                        
                        OUTPUT:
                        
                        Return a structured response matching the
                        CodeReviewResponse schema.
                        
                        Each finding must contain:
                        - severity
                        - category
                        - filePath
                        - lineNumber
                        - title
                        - description
                        - suggestedFix
                        - confidence
                        
                        If no actionable findings exist, return an empty
                        findings collection.
                        """)
                .user("""
                        Review this GitHub Pull Request unified diff.
                        
                        Review scope: All files and all languages represented
                        in the diff.
                        
                        File metadata: %s
                        Language metadata: %s
                        
                        The language metadata is only a hint. The diff may contain
                        multiple languages and file types.
                        
                        <pull_request_diff>
                        %s
                        </pull_request_diff>
                        """.formatted(
                        request.fileName(),
                        request.language(),
                        request.code()
                ))
                .call()
                .entity(CodeReviewResponse.class);
    }
}
