package com.sistema.examenes.exams.interfaces.rest.transform;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import com.sistema.examenes.exams.interfaces.rest.resources.CategoryResource;

public class CategoryResourceFromEntityAssembler {
    public static CategoryResource toResourceFromEntity(Category entity) {
        return new CategoryResource(entity.getId(), entity.getTitle(), entity.getDescription());
    }
}
