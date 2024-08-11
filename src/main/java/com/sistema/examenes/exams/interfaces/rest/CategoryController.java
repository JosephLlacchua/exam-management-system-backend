package com.sistema.examenes.exams.interfaces.rest;

import com.sistema.examenes.exams.domain.model.queries.GetAllCategoriesQuery;
import com.sistema.examenes.exams.domain.model.queries.GetCategoryByIdQuery;
import com.sistema.examenes.exams.domain.services.CategoryCommandService;
import com.sistema.examenes.exams.domain.services.CategoryQueryService;
import com.sistema.examenes.exams.interfaces.rest.resources.CategoryResource;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateCategoryResource;
import com.sistema.examenes.exams.interfaces.rest.transform.CategoryResourceFromEntityAssembler;
import com.sistema.examenes.exams.interfaces.rest.transform.CreateCategoryFromResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories")
@Tag(name = "Categories", description = "Category Management Endpoints")
public class CategoryController {
    private final CategoryQueryService categoryQueryService;
    private final CategoryCommandService categoryCommandService;

    public CategoryController(CategoryQueryService categoryQueryService, CategoryCommandService categoryCommandService) {
        this.categoryQueryService = categoryQueryService;
        this.categoryCommandService = categoryCommandService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResource>> getAllCategories() {
        var getAllCategoriesQuery = new GetAllCategoriesQuery();
        var categories = categoryQueryService.handle(getAllCategoriesQuery);
        var categoryResources = categories.stream().map(CategoryResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(categoryResources);
    }

    @GetMapping(value = "/{categoryId}")
    public ResponseEntity<CategoryResource> getCategoryById(@PathVariable Long categoryId){
        var getCategoryByIdQuery=new GetCategoryByIdQuery(categoryId);
        var category=categoryQueryService.handle(getCategoryByIdQuery);
        if(category.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var categoryResource=CategoryResourceFromEntityAssembler.toResourceFromEntity(category.get());
        return ResponseEntity.ok(categoryResource);
    }

    @PostMapping
    public ResponseEntity<CategoryResource> createCategory(@RequestBody CreateCategoryResource resource){
        var createCategoryCommand = CreateCategoryFromResourceAssembler.toCommandFromResource(resource);
        var category= categoryCommandService.handle(createCategoryCommand);
        if(category.isEmpty())
            return ResponseEntity.badRequest().build();
        var categoryResource=CategoryResourceFromEntityAssembler.toResourceFromEntity(category.get());
        return new ResponseEntity<>(categoryResource,org.springframework.http.HttpStatus.CREATED);

    }
}
