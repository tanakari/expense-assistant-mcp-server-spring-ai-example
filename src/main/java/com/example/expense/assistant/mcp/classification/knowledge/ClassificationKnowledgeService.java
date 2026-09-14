package com.example.expense.assistant.mcp.classification.knowledge;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ClassificationKnowledgeService {

    private final ClassificationKnowledgeRepository repository;

    public ClassificationKnowledgeService(ClassificationKnowledgeRepository repository) {
        this.repository = repository;
    }

    public List<ClassificationKnowledge> fetchAll() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(this::toDomain)
                .toList();
    }

    public ClassificationKnowledge register(
            String merchant,
            String description,
            String purpose,
            String expenseCategory,
            String reason) {

        if (!StringUtils.hasText(purpose)) {
            throw new IllegalArgumentException("purpose は必須です。");
        }
        if (!StringUtils.hasText(expenseCategory)) {
            throw new IllegalArgumentException("expenseCategory は必須です。");
        }

        ClassificationKnowledge knowledge = new ClassificationKnowledge(
                UUID.randomUUID().toString(),
                blankToNull(merchant),
                blankToNull(description),
                purpose.trim(),
                expenseCategory.trim(),
                blankToNull(reason),
                OffsetDateTime.now(ZoneOffset.UTC));

        return toDomain(repository.save(toEntity(knowledge)));
    }

    private String blankToNull(String value) {
        return StringUtils.hasText(value) ? value.trim() : null;
    }

    private ClassificationKnowledge toDomain(ClassificationKnowledgeEntity entity) {
        return new ClassificationKnowledge(
                entity.getId(),
                entity.getMerchant(),
                entity.getDescription(),
                entity.getPurpose(),
                entity.getExpenseCategory(),
                entity.getReason(),
                entity.getCreatedAt());
    }

    private ClassificationKnowledgeEntity toEntity(ClassificationKnowledge knowledge) {
        ClassificationKnowledgeEntity entity = new ClassificationKnowledgeEntity();
        entity.setId(knowledge.id());
        entity.setMerchant(knowledge.merchant());
        entity.setDescription(knowledge.description());
        entity.setPurpose(knowledge.purpose());
        entity.setExpenseCategory(knowledge.expenseCategory());
        entity.setReason(knowledge.reason());
        entity.setCreatedAt(knowledge.createdAt());
        return entity;
    }
}
