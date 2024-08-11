package com.sistema.examenes.exams.interfaces.rest.resources;

public record QuestionResource (
        Long id,
        String content,
        String option1,
        String option2,
        String option3,
        String option4,
        String correctAnswer,
        Long examId
        ){
}