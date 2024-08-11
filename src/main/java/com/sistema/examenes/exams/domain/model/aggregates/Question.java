package com.sistema.examenes.exams.domain.model.aggregates;

import com.sistema.examenes.exams.domain.model.commands.CreateQuestionCommand;
import com.sistema.examenes.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Question extends AuditableAbstractAggregateRoot<Question> {

    @NotBlank
    @Size(max=500)
    private String content;

    @NotBlank
    @Size(max=50)
    private String option1;

    @NotBlank
    @Size(max=50)
    private String option2;

    @NotBlank
    @Size(max=50)
    private String option3;

    @NotBlank
    @Size(max=50)
    private String option4;

    @NotBlank
    @Size(max=50)
    private String correctAnswer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Exam exam;

    public Question() {
    }

    public Question(CreateQuestionCommand command ,Exam exam){
        this.content=command.content();
        this.option1=command.option1();
        this.option2=command.option2();
        this.option3=command.option3();
        this.option4=command.option4();
        this.correctAnswer=command.correctAnswer();
        this.exam=exam;
    }


}
