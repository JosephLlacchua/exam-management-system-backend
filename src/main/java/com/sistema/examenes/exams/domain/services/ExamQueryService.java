package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

public interface ExamQueryService {
    List<Exam>handle(GetAllExamsQuery query);
    List<Exam> handle(GetAllExamsActiveQuery query);
    List<Exam>handle(GetAllExamsByCategoryIdQuery query);
    List<Exam>handle(GetAllExamsByCategoryIdAndActiveQuery query);
    Optional<Exam>handle(GetExamByIdQuery query);
}
