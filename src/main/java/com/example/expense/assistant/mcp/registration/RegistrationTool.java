package com.example.expense.assistant.mcp.registration;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

@Component
public class RegistrationTool {

    private final RegistrationService registrationService;

    public RegistrationTool(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @McpTool(
            name = "register_expense",
            description = """
                    利用日、支出先、購入内容・摘要、利用目的、経費科目、合計金額、税額を経費として登録します。
                    今回の領収書について get_expense_processing_workflow で取得した手順に従い、
                    入力確認、科目判定、結果提示を終えてから使用してください。
                    登録する全項目を提示した後の新しい返答で、ユーザーが今回の内容での登録に明示的に同意した場合のみ呼び出してください。
                    最初の「登録して」という依頼だけでは呼び出さず、確認を求めた同じ応答内でも呼び出さないでください。
                    登録内容が不足している場合は先に確認し、内容が修正された場合は、修正後の内容を再提示して確認してください。
                    前回の依頼や同意、ナレッジ登録への同意を今回の経費登録への同意として扱わないでください。
                    本アプリでは実際の登録処理は行わず、モックとして実装しています。
                    """,
            generateOutputSchema = true)
    public RegistrationResponse registerExpense(
            @McpToolParam(description = "利用日。YYYY-MM-DD形式で指定してください。", required = true) String date,
            @McpToolParam(description = "支出先", required = true) String merchant,
            @McpToolParam(description = "購入内容や摘要", required = true) String description,
            @McpToolParam(description = "ユーザー入力や確認で判明した利用目的", required = true) String purpose,
            @McpToolParam(description = "判定ルールとユーザー入力・回答から確定した経費科目", required = true) String expenseCategory,
            @McpToolParam(description = "合計金額。円単位の整数で指定してください。", required = true) int total,
            @McpToolParam(description = "税額。分からない場合は省略可能です。", required = false) Integer tax) {

        Expense expense = registrationService.register(
                date,
                merchant,
                description,
                purpose,
                expenseCategory,
                total,
                tax);

        return new RegistrationResponse(
                "経費登録が完了しました。",
                expense);
    }
}
