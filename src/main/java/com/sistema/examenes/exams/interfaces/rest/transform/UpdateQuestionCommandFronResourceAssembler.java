package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.commands.UpdateQuestionCommand;
import com.sistema.examenes.exams.interfaces.rest.resources.UpdateQuestionResource;

public class UpdateQuestionCommandFronResourceAssembler {
    public static UpdateQuestionCommand toCommandFromResource(Long questionId, UpdateQuestionResource resource) {
        return new UpdateQuestionCommand(
                questionId,
                resource.content(),
                resource.option1(),
                resource.option2(),
                resource.option3(),
                resource.option4(),
                resource.correctAnswer(),
                resource.examId()
        );
    }
}
