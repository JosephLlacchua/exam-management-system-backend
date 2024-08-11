package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.commands.CreateQuestionCommand;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateQuestionResource;

public class CreateQuestionFromResourceAssembler {
    public static CreateQuestionCommand toCommandFromResource(CreateQuestionResource resource){
        return new CreateQuestionCommand(
                resource.content(),
                resource.option1(),
                resource.option2(),
                resource.option3(),
                resource.option4(),
                resource.correctAnswer(),
                resource.examId());
    }
}
