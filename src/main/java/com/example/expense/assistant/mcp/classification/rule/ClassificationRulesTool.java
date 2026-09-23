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
                    経費科目を判定するための業務ルールを取得します。
                    """,
            generateOutputSchema = true)
    public ClassificationRulesResponse getClassificationRules() {
        return new ClassificationRulesResponse(
                classificationRulesService.getClassificationRules());
    }
}
