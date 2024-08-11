package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import com.sistema.examenes.exams.domain.model.commands.CreateCategoryCommand;

import java.util.Optional;

public interface CategoryCommandService {
    Optional<Category>handle(CreateCategoryCommand command);

}
