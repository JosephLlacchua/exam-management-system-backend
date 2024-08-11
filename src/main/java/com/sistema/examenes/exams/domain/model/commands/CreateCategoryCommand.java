package com.sistema.examenes.exams.domain.model.commands;

public record CreateCategoryCommand(
        String title,
        String description
) {
}
