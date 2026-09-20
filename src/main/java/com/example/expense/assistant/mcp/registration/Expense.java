package com.example.expense.assistant.mcp.registration;

public record Expense(
    String id,
    String date,
    String merchant,
    String description,
    String purpose,
    String expenseCategory,
    int total,
    Integer tax) {
}
