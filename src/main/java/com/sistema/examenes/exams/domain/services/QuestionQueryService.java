package com.sistema.examenes.exams.domain.services;

import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsByExamIdQuery;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetQuestionByIdQuery;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionQueryService {
    List<Question> handle(GetAllQuestionsQuery query);
    Optional<Question>handle(GetQuestionByIdQuery query);
    Set<Question>handle(GetAllQuestionsByExamIdQuery query);


}
