package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.interfaces.rest.resources.QuestionResource;

public class QuestionResourceFromEntityAssembler {
    public static QuestionResource toResourceFromEntity(Question entity) {
        return new QuestionResource(
                entity.getId(),
                entity.getContent(),
                entity.getOption1(),
                entity.getOption2(),
                entity.getOption3(),
                entity.getOption4(),
                entity.getCorrectAnswer(),
                entity.getExam().getId()
        );
    }
}
