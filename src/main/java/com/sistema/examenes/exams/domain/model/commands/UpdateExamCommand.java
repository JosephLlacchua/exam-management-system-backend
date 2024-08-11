package com.sistema.examenes.exams.domain.model.commands;

public record UpdateExamCommand(
        Long examId,
        String title,
        String description,
        String maxPoints,
        String numberOfQuestions,
        Boolean active,
        Long categoryId) {
}
