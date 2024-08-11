package com.sistema.examenes.exams.domain.model.aggregates;

import com.sistema.examenes.exams.domain.model.commands.CreateExamCommand;
import com.sistema.examenes.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Setter
@Getter
public class Exam extends AuditableAbstractAggregateRoot<Exam> {

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 120)
    private String description;

    @NotBlank
    private String maxPoints;

    @NotBlank
    private String numberOfQuestions;

    @NotNull
    private Boolean active=false;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;


    @OneToMany(mappedBy = "exam",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Question> questions= new LinkedHashSet<>();

    public Exam() {
    }

    public Exam(CreateExamCommand command , Category category) {
        this.title=command.title();
        this.description=command.description();
        this.maxPoints=command.maxPoints();
        this.numberOfQuestions=command.numberOfQuestions();
        this.active=command.active();
        this.category=category;

    }


}
