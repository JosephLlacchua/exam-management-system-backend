package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.commands.UpdateExamCommand;
import com.sistema.examenes.exams.interfaces.rest.resources.UpdateExamResource;

public class UpdateExamCommandFromResourceAssembler {
    public static UpdateExamCommand toCommandFromResource(Long examId, UpdateExamResource resource) {
        return new UpdateExamCommand(
                examId,
                resource.title(),
                resource.description(),
                resource.maxPoints(),
                resource.numberOfQuestions(),
                resource.active(),
                resource.categoryId()
        );
    }
}
