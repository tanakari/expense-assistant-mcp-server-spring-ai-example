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
                    """,
            generateOutputSchema = true)
    public ProcessingWorkflowResponse getProcessingWorkflow() {
        return new ProcessingWorkflowResponse(workflowService.getWorkflow());
    }
}
