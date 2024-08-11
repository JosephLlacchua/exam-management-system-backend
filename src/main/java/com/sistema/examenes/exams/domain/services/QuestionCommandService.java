package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.domain.model.commands.CreateQuestionCommand;
import com.sistema.examenes.exams.domain.model.commands.DeleteQuestionCommand;
import com.sistema.examenes.exams.domain.model.commands.UpdateQuestionCommand;

import java.util.Optional;

public interface QuestionCommandService {
    Optional<Question>handle(CreateQuestionCommand command);
    Optional<Question>handle(UpdateQuestionCommand command);
    void handle(DeleteQuestionCommand command);
}
