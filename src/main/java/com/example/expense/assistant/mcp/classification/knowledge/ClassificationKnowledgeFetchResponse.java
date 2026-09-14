package com.example.expense.assistant.mcp.classification.knowledge;

import java.util.List;

public record ClassificationKnowledgeFetchResponse(
        String message,
        List<ClassificationKnowledge> results) {
}