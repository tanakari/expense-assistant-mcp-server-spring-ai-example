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
                    本サンプルでは実際の保存処理を行わず、入力内容に識別子を付けた登録結果を返します。
                    """,
            generateOutputSchema = false)
    public RegistrationResponse registerExpense(
            @McpToolParam(description = "利用日。YYYY-MM-DD形式で指定してください。", required = true) String date,
            @McpToolParam(description = "支出先", required = true) String merchant,
            @McpToolParam(description = "購入内容や摘要", required = true) String description,
            @McpToolParam(description = "ユーザー入力や確認で判明した利用目的", required = true) String purpose,
            @McpToolParam(description = "判定ルールとユーザー入力・回答から確定した経費科目", required = true) String expenseCategory,
            @McpToolParam(description = "合計金額。円単位の整数で指定してください。", required = true) int total,
            @McpToolParam(description = "税額。領収書などで確認できない場合は推測せず、省略してください。", required = false) Integer tax) {

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
