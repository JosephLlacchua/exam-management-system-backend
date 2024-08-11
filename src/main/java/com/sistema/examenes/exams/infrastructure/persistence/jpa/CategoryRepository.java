package com.sistema.examenes.exams.infrastructure.persistence.jpa;

import com.sistema.examenes.exams.domain.model.aggregates.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
    boolean existsByTitle(String title);
}
