package com.sistema.examenes.exams.application.internal.commandservices;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import com.sistema.examenes.exams.domain.model.commands.CreateCategoryCommand;
import com.sistema.examenes.exams.domain.services.CategoryCommandService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final CategoryRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> handle(CreateCategoryCommand command) {
        boolean categoriaExists=categoryRepository.existsByTitle(command.title());
        if(categoriaExists) {
            throw new RuntimeException("Category with this title already exists");
        }
        var category=new Category(command);
        try{
            categoryRepository.save(category);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while saving category: "+e.getMessage());
        }
        return Optional.of(category);
    }
}
