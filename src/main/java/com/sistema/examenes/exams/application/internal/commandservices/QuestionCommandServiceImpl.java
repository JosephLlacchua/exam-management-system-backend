package com.sistema.examenes.exams.application.internal.commandservices;

import com.sistema.examenes.exams.domain.model.aggregates.Question;
import com.sistema.examenes.exams.domain.model.commands.CreateQuestionCommand;
import com.sistema.examenes.exams.domain.model.commands.DeleteQuestionCommand;
import com.sistema.examenes.exams.domain.model.commands.UpdateQuestionCommand;
import com.sistema.examenes.exams.domain.services.QuestionCommandService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.ExamRepository;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionCommandServiceImpl  implements QuestionCommandService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public QuestionCommandServiceImpl(QuestionRepository questionRepository, ExamRepository examRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
    }

    @Override
    public Optional<Question> handle(CreateQuestionCommand command) {
        var exam= examRepository.findById(command.examId());
        if (exam.isEmpty()) {
            throw new RuntimeException("Exam does not exist");
        }
        if(questionRepository.existsByContent(command.content())){
            throw new IllegalArgumentException("Question with same content already exists");
        }
        var question = new Question(command, exam.get());
        try {
            questionRepository.save(question);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving question: " + e.getMessage());
        }
        return Optional.of(question);
    }

    @Override
    public Optional<Question> handle(UpdateQuestionCommand command) {
        Optional<Question> questionActual = questionRepository.findById(command.questionId());
        if(questionActual.isPresent()){  //
            Question question = questionActual.get();
            question.setContent(command.content());
            question.setOption1(command.option1());
            question.setOption2(command.option2());
            question.setOption3(command.option3());
            question.setOption4(command.option4());
            question.setCorrectAnswer(command.correctAnswer());
            // Buscar el examen por su ID
            var exam = examRepository.findById(command.examId());
            if(exam.isEmpty()) {
                throw new RuntimeException("Exam does not exist");
            }
            // Establecer el examen en la pregunta
            question.setExam(exam.get());
            try {
                questionRepository.save(question);
                return Optional.of(question);
            }catch (Exception e){
                throw new IllegalArgumentException("Error while updating question: "+e.getMessage());
            }
        }else {
            return Optional.empty();
        }
    }

    @Override
    public void handle(DeleteQuestionCommand command) {
        if(!questionRepository.existsById(command.questionId())){
            throw new IllegalArgumentException("Question does not exist");
        }
        try{
            questionRepository.deleteById(command.questionId());
        }catch (Exception e){
            throw new IllegalArgumentException("Error while deleting question: "+e.getMessage());
        }

    }
}
