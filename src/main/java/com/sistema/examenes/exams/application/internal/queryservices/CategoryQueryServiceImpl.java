package com.sistema.examenes.exams.application.internal.queryservices;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import com.sistema.examenes.exams.domain.model.queries.GetAllCategoriesQuery;
import com.sistema.examenes.exams.domain.model.queries.GetCategoryByIdQuery;
import com.sistema.examenes.exams.domain.services.CategoryQueryService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> handle(GetAllCategoriesQuery query) {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> handle(GetCategoryByIdQuery query) {
        return categoryRepository.findById(query.categoryId());
    }
}
