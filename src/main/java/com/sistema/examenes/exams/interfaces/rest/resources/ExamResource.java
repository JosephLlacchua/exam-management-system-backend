package com.sistema.examenes.exams.interfaces.rest.resources;

public record ExamResource(
        Long id,
        String title,
        String description,
        String maxPoints,
        String numberOfQuestions,
        Boolean active,
        Long categoryId
) {
}
