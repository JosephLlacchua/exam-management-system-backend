package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.queries.GetAllExamsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetExamByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ExamQueryService {
    List<Exam>handle(GetAllExamsQuery query);
    Optional<Exam>handle(GetExamByIdQuery query);
}
