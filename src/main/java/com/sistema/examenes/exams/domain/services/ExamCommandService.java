package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.commands.CreateExamCommand;
import com.sistema.examenes.exams.domain.model.commands.DeleteExamCommand;
import com.sistema.examenes.exams.domain.model.commands.UpdateExamCommand;

import java.util.Optional;

public interface ExamCommandService {
    Optional<Exam>handle(CreateExamCommand command);
    Optional<Exam>handle(UpdateExamCommand command);
    void handle(DeleteExamCommand command);}
