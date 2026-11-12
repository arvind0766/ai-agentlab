
package com.arvind.javaaiagentlab.controller;

import com.arvind.javaaiagentlab.dto.CodeReviewRequest;
import com.arvind.javaaiagentlab.dto.CodeReviewResponse;
import com.arvind.javaaiagentlab.service.CodeReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/code-review")
public class CodeReviewController {

    private final CodeReviewService codeReviewService;

    public CodeReviewController(CodeReviewService codeReviewService) {
        this.codeReviewService = codeReviewService;
    }

    @PostMapping
    public CodeReviewResponse review(
            @RequestBody CodeReviewRequest request) {

        return codeReviewService.review(request);
    }
}
