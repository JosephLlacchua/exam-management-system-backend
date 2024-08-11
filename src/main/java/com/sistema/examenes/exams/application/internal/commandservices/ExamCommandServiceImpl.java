package com.sistema.examenes.exams.application.internal.commandservices;

import com.sistema.examenes.exams.domain.model.aggregates.Exam;
import com.sistema.examenes.exams.domain.model.commands.CreateExamCommand;
import com.sistema.examenes.exams.domain.model.commands.DeleteExamCommand;
import com.sistema.examenes.exams.domain.model.commands.UpdateExamCommand;
import com.sistema.examenes.exams.domain.services.ExamCommandService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.CategoryRepository;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExamCommandServiceImpl implements ExamCommandService{
    private final ExamRepository examRepository;
    private final CategoryRepository categoryRepository;

    public ExamCommandServiceImpl(ExamRepository examRepository, CategoryRepository categoryRepository) {
        this.examRepository = examRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Exam> handle(CreateExamCommand command) {
        var category=categoryRepository.findById(command.categoryId());
        if(category.isEmpty()) {
            throw new RuntimeException("Category does not exist");
        }
        var exam=new Exam(command,category.get());
        try {
            examRepository.save(exam);
        }catch (Exception e){
            throw new IllegalArgumentException("Error while saving exam: "+e.getMessage());
        }
        return Optional.of(exam);
    }

    @Override
    public Optional<Exam> handle(UpdateExamCommand command) {
        Optional<Exam> examActual=examRepository.findById(command.examId());
        if(examActual.isPresent()){
            Exam exam=examActual.get();
            exam.setTitle(command.title());
            exam.setDescription(command.description());
            exam.setMaxPoints(command.maxPoints());
            exam.setNumberOfQuestions(command.numberOfQuestions());
            exam.setActive(command.active());
            // Buscar la categoría por su ID
            var category = categoryRepository.findById(command.categoryId());
            if(category.isEmpty()) {
                throw new RuntimeException("Category does not exist");
            }
            // Establecer la categoría en el examen
            exam.setCategory(category.get());
            try {
                examRepository.save(exam);
                return Optional.of(exam);
            }catch (Exception e){
                throw new IllegalArgumentException("Error while updating exam: "+e.getMessage());
            }
        }else {
            return Optional.empty();
        }

    }

    @Override
    public void handle(DeleteExamCommand command) {
        if(!examRepository.existsById(command.examId())){ //es para que no se pueda eliminar un examen que no existe
            throw new IllegalArgumentException("Exam does not exist");
        }
        try {
            examRepository.deleteById(command.examId());
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting exam: "+e.getMessage());
        }

    }
}
