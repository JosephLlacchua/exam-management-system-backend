package com.sistema.examenes.exams.infrastructure.persistence.jpa;

import com.sistema.examenes.exams.domain.model.aggregates.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionRepository  extends JpaRepository<Question,Long> {
    Set<Question> findAllByExamId(Long examId);
    boolean existsByContent(String content);
}
