package com.example.expense.assistant.mcp.workflow;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.stereotype.Component;

@Component
public class ProcessingWorkflowTool {

    private final ProcessingWorkflowService workflowService;

    public ProcessingWorkflowTool(ProcessingWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @McpTool(
            name = "get_expense_processing_workflow",
            description = """
                    経費科目の判定や経費処理で使用するワークフローを取得します。
                    新しい領収書を受け取るたびに、ほかの経費処理Toolより先に呼び出してください。
                    同じ会話内で類似する領収書を処理済みでも省略しないでください。
                    取得後はワークフローを手順1から実行し、判定結果の提示後も、経費登録とナレッジ登録に該当する条件を確認してください。
                    """,
            generateOutputSchema = true)
    public ProcessingWorkflowResponse getProcessingWorkflow() {
        return new ProcessingWorkflowResponse(workflowService.getWorkflow());
    }
}
