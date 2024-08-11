package com.sistema.examenes.exams.domain.model.aggregates;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.examenes.exams.domain.model.commands.CreateCategoryCommand;
import com.sistema.examenes.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Setter
@Getter
public class Category extends AuditableAbstractAggregateRoot<Category> {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 120)
    private String description;

    @OneToMany(mappedBy = "category",cascade = CascadeType.REMOVE)
    @JsonIgnore //Evitar la recursividad
    private Set<Exam> exams= new LinkedHashSet<>();

    public Category() {
    }

    public Category(CreateCategoryCommand command){
        this.title=command.title();
        this.description=command.description();
    }


}
