package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.commands.CreateCategoryCommand;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateCategoryResource;

public class CreateCategoryFromResourceAssembler {
    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource){
        return new CreateCategoryCommand(resource.title(),resource.description());

    }
}
