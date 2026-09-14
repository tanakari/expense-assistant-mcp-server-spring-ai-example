package com.example.expense.assistant.mcp.workflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service 
public class ProcessingWorkflowService {

    private static final String WORKFLOW_RESOURCE = "workflow/processing.md";

    public String getWorkflow() {
        return read(WORKFLOW_RESOURCE);
    }
    
    private String read(String path) {
        try (var inputStream =
                     new ClassPathResource(path).getInputStream()) {

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "経費科目の判定ワークフローの読み込みに失敗しました: " + path,
                    e);
        }
    }
}
