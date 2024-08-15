package com.sistema.examenes.exams.infrastructure.persistence.jpa;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findAllByActiveTrue();

}
