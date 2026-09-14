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
                    経費科目の判定や経費処理を行うときに従うワークフローを取得します。
                    経費処理を開始するときに最初に呼び出してください。
                    """,
            generateOutputSchema = true)
    public ProcessingWorkflowResponse getProcessingWorkflow() {
        return new ProcessingWorkflowResponse(workflowService.getWorkflow());
    }
}
