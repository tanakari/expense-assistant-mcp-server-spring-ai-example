package com.example.expense.assistant.mcp.classification.rule;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

@Component
public class ClassificationRulesTool {

    private final ClassificationRulesService
            classificationRulesService;

    public ClassificationRulesTool(
            ClassificationRulesService classificationRulesService) {
        this.classificationRulesService = classificationRulesService;
    }

    @McpTool(
            name = "get_expense_classification_rules",
            description = """
                    経費科目を判定するためのルールを取得します。
                    経費科目を判定するときに使用してください。
                    取得したルールを業務上の基準とし、ナレッジより優先して経費科目を判定してください。
                    判断に必要な情報が不足している場合は、推測で確定せずユーザーへ確認してください。
                    """,
            generateOutputSchema = true)
    public ClassificationRulesResponse getClassificationRules() {
        return new ClassificationRulesResponse(
                classificationRulesService.getClassificationRules());
    }
}
