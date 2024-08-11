package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.commands.CreateExamCommand;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateExamResource;

public class CreateExamFromResourceAssembler {
    public static CreateExamCommand toCommandFromResource(CreateExamResource resource){
        return new CreateExamCommand(
                resource.title(),
                resource.description(),
                resource.maxPoints(),
                resource.numberOfQuestions(),
                resource.active(),
                resource.categoryId()
        );
    }
}
