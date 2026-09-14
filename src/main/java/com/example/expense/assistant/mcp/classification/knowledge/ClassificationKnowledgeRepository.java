package com.example.expense.assistant.mcp.classification.knowledge;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassificationKnowledgeRepository extends JpaRepository<ClassificationKnowledgeEntity, String> {

    List<ClassificationKnowledgeEntity> findAllByOrderByCreatedAtDesc();

}
