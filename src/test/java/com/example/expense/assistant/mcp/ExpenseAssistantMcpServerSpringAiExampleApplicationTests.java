package com.example.expense.assistant.mcp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.expense.assistant.mcp.classification.knowledge.ClassificationKnowledge;
import com.example.expense.assistant.mcp.classification.knowledge.ClassificationKnowledgeService;

@SpringBootTest
@Transactional
class ExpenseAssistantMcpServerSpringAiExampleApplicationTests {

    @Autowired
    private ClassificationKnowledgeService knowledgeService;

    @Test
    void registeredKnowledgeCanBeFetched() {
        ClassificationKnowledge registered = knowledgeService.register(
                "サンプル書店",
                "Java技術書",
                "業務で利用するため",
                "新聞図書費",
                "業務上必要な技術書のため");

        assertThat(knowledgeService.fetchAll())
                .extracting(ClassificationKnowledge::id)
                .contains(registered.id());
    }

    @Test
    void purposeIsRequired() {
        assertThatThrownBy(() -> knowledgeService.register(
                "サンプル書店",
                "Java技術書",
                " ",
                "新聞図書費",
                null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("purpose は必須です。");
    }

    @Test
    void expenseCategoryIsRequired() {
        assertThatThrownBy(() -> knowledgeService.register(
                "サンプル書店",
                "Java技術書",
                "業務で利用するため",
                null,
                null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("expenseCategory は必須です。");
    }
}
