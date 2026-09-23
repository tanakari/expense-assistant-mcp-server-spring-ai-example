package com.example.expense.assistant.mcp.classification.knowledge;

import java.util.List;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

// ClassificationKnowledge には nullable フィールドが含まれる。
// Spring AI issue #6950 により、nullable フィールドを含むレスポンスでは
// Output Schema が正しく生成されないため、暫定的に generateOutputSchema を無効化する。
@Component
public class ClassificationKnowledgeTool {

    private final ClassificationKnowledgeService knowledgeService;

    public ClassificationKnowledgeTool(ClassificationKnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @McpTool(
            name = "fetch_expense_knowledge",
            description = """
                    経費科目判定用ナレッジの一覧を取得します。
                    各ナレッジには、支出先、購入内容・摘要、利用目的、経費科目、判定理由が含まれます。
                    """,
            generateOutputSchema = false)
    public ClassificationKnowledgeFetchResponse fetchKnowledge() {
        List<ClassificationKnowledge> results = knowledgeService.fetchAll();

        return new ClassificationKnowledgeFetchResponse(
                "経費科目判定用ナレッジを%s件取得しました。".formatted(results.size()),
                results);
    }

    @McpTool(
            name = "register_expense_knowledge",
            description = """
                    支出先、購入内容・摘要、利用目的、経費科目、判定理由を、経費科目判定用ナレッジとして登録します。
                    登録したナレッジを返します。
                    """,
            generateOutputSchema = false)
    public ClassificationKnowledgeRegistrationResponse registerKnowledge(
            @McpToolParam(description = "支出先。分からない場合は省略可能です。", required = false) String merchant,
            @McpToolParam(description = "購入内容や摘要。分からない場合は省略可能です。", required = false) String description,
            @McpToolParam(description = "ユーザー確認で判明した利用目的", required = true) String purpose,
            @McpToolParam(description = "判定ルールとユーザー回答から確定した経費科目", required = true) String expenseCategory,
            @McpToolParam(description = "その経費科目と判断した理由。省略可能です。", required = false) String reason) {

        ClassificationKnowledge knowledge = knowledgeService.register(
                merchant,
                description,
                purpose,
                expenseCategory,
                reason);

        return new ClassificationKnowledgeRegistrationResponse(
                "経費科目判定用ナレッジを登録しました。",
                knowledge);
    }
}
