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
                    領収書の入力確認、経費科目判定、経費登録、判定用ナレッジ登録の手順を記載したワークフローを取得します。
                    """,
            generateOutputSchema = true)
    public ProcessingWorkflowResponse getProcessingWorkflow() {
        return new ProcessingWorkflowResponse(workflowService.getWorkflow());
    }
}
