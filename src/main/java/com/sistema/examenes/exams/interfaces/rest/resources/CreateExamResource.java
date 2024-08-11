package com.sistema.examenes.exams.interfaces.rest.resources;

public record CreateExamResource(
        String title,
        String description,
        String maxPoints,
        String numberOfQuestions,
        Boolean active,
        Long categoryId
) {
}
