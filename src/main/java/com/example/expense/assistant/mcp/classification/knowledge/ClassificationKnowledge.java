package com.example.expense.assistant.mcp.classification.knowledge;

import java.time.OffsetDateTime;

public record ClassificationKnowledge(
    String id,
    String merchant,
    String description,
    String purpose,
    String expenseCategory,
    String reason,
    OffsetDateTime createdAt) {

}
