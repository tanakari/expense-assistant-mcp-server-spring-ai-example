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
                    経費科目判定用のナレッジを取得します。
                    ナレッジは経費科目判定の補助情報として利用し、判定ルールと今回のユーザー入力を優先してください。
                    ナレッジだけを根拠に経費科目を確定しないでください。
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
                    判定ルールと既存のナレッジだけでは判断できず、ユーザーへの確認によって経費科目を確定できた場合に、
                    今後の判定にも再利用できる情報をナレッジとして登録します。
                    ユーザー確認で得た情報だけを登録対象とし、LLMの推測だけで得た内容は登録しないでください。
                    登録する全項目を提示した後の新しい返答で、ユーザーが明示的に同意した場合のみ呼び出してください。
                    最初の登録依頼だけでは呼び出さず、確認を求めた同じ応答内でも呼び出さないでください。
                    内容が修正された場合は、修正後の内容を再提示して確認してください。
                    前回の同意や、経費登録への同意をナレッジ登録への同意として扱わないでください。
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
