package com.sistema.examenes.exams.domain.model.commands;


public record CreateQuestionCommand(

        String content,
        String option1,
        String option2,
        String option3,
        String option4,
        String correctAnswer,
        Long examId
) {
}
