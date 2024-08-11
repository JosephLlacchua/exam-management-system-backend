package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.interfaces.rest.resources.ExamResource;

public class ExamResourceFromEntityAssembler {
    public static ExamResource toResourceFromEntity(Exam entity) {
        return new ExamResource(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getMaxPoints(),
                entity.getNumberOfQuestions(),
                entity.getActive(),
                entity.getCategory().getId());
    }
}
