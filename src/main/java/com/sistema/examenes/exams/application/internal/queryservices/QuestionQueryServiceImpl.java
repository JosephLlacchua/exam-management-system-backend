package com.sistema.examenes.exams.application.internal.queryservices;

import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsByExamIdQuery;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetQuestionByIdQuery;
import com.sistema.examenes.exams.domain.services.QuestionQueryService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.ExamRepository;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class QuestionQueryServiceImpl  implements QuestionQueryService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionQueryServiceImpl(QuestionRepository questionRepository, ExamRepository examRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }


    @Override
    public List<Question> handle(GetAllQuestionsQuery query) {
        return this.questionRepository.findAll();
    }

    @Override
    public Optional<Question> handle(GetQuestionByIdQuery query) {
        return this.questionRepository.findById(query.questionId());
    }

    @Override
    public Set<Question> handle(GetAllQuestionsByExamIdQuery query) {
        if(this.examRepository.findById(query.examId()).isEmpty()){
            throw new IllegalArgumentException("The exam does not exist");
        }
        return this.questionRepository.findAllByExamId(query.examId());
    }
}
