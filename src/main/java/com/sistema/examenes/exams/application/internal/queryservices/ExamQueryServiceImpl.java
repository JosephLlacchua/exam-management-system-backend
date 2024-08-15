package com.sistema.examenes.exams.application.internal.queryservices;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.domain.model.queries.GetAllExamsActiveQuery;
import com.sistema.examenes.exams.domain.model.queries.GetAllExamsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetExamByIdQuery;
import com.sistema.examenes.exams.domain.services.ExamQueryService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExamQueryServiceImpl implements ExamQueryService{

    private final ExamRepository examRepository;

    public ExamQueryServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public List<Exam> handle(GetAllExamsQuery query) {
        return this.examRepository.findAll();
    }

    @Override
    public List<Exam> handle(GetAllExamsActiveQuery query) {
        return this.examRepository.findAllByActiveTrue();
    }

    @Override
    public Optional<Exam> handle(GetExamByIdQuery query) {
        return this.examRepository.findById(query.examId());
    }
}
