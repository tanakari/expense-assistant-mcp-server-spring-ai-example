package com.example.expense.assistant.mcp.classification.rule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ClassificationRulesService {

    private static final String CLASSIFICATION_RULES =
            "classification/classification-rules.md";

    public String getClassificationRules() {
        return read(CLASSIFICATION_RULES);
    }

    private String read(String path) {
        try (var inputStream =
                     new ClassPathResource(path).getInputStream()) {

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "経費科目の判定ルールの読み込みに失敗しました: " + path,
                    e);
        }
    }
}
