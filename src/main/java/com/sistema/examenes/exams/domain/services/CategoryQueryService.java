package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import com.sistema.examenes.exams.domain.model.queries.GetAllCategoriesQuery;
import com.sistema.examenes.exams.domain.model.queries.GetCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryService {
    List<Category>handle(GetAllCategoriesQuery query);
    Optional<Category>handle(GetCategoryByIdQuery query);
}
