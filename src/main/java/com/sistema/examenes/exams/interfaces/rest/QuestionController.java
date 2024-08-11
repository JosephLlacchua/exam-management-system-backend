package com.sistema.examenes.exams.interfaces.rest;


import com.sistema.examenes.exams.domain.model.commands.DeleteQuestionCommand;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsByExamIdQuery;
import com.sistema.examenes.exams.domain.model.queries.GetAllQuestionsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetQuestionByIdQuery;
import com.sistema.examenes.exams.domain.services.QuestionCommandService;
import com.sistema.examenes.exams.domain.services.QuestionQueryService;
import com.sistema.examenes.exams.infrastructure.persistence.jpa.ExamRepository;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateQuestionResource;
import com.sistema.examenes.exams.interfaces.rest.resources.QuestionResource;
import com.sistema.examenes.exams.interfaces.rest.resources.UpdateQuestionResource;
import com.sistema.examenes.exams.interfaces.rest.transform.CreateQuestionFromResourceAssembler;
import com.sistema.examenes.exams.interfaces.rest.transform.QuestionResourceFromEntityAssembler;
import com.sistema.examenes.exams.interfaces.rest.transform.UpdateQuestionCommandFronResourceAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/api/v1/questions")
@Tag(name = "Questions", description = "Question Management Endpoints")
public class QuestionController {

    private final QuestionQueryService questionQueryService;
    private final QuestionCommandService questionCommandService;
    private final ExamRepository examRepository;

    public QuestionController(QuestionQueryService questionQueryService, QuestionCommandService questionCommandService, ExamRepository examRepository) {
        this.questionQueryService = questionQueryService;
        this.questionCommandService = questionCommandService;
        this.examRepository = examRepository;
    }

    @GetMapping
    public ResponseEntity< List<QuestionResource>> getAllQuestions(){
        var getAllQuestionsQuery = new GetAllQuestionsQuery();
        var questions = questionQueryService.handle(getAllQuestionsQuery);
        var questionResources=questions.stream().map(QuestionResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(questionResources);
    }

    @GetMapping(value = "/{questionId}")
    public ResponseEntity<QuestionResource> getQuestionById(@PathVariable Long questionId){
        var getQuestionByIdQuery = new GetQuestionByIdQuery(questionId);
        var question = questionQueryService.handle(getQuestionByIdQuery);
        if(question.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var questionResource = QuestionResourceFromEntityAssembler.toResourceFromEntity(question.get());
        return ResponseEntity.ok(questionResource);
    }

    @GetMapping(value = "/exam/{examId}")
    public ResponseEntity<?> getAllQuestionsByExamId(@PathVariable Long examId){
        var exam = examRepository.findById(examId);
        if (exam.isEmpty()) {
            return ResponseEntity.badRequest().body("El examen con el ID " + examId + " no existe.");
        }
        var getAllQuestionsByExamIdQuery = new GetAllQuestionsByExamIdQuery(examId);
        var questions = questionQueryService.handle(getAllQuestionsByExamIdQuery);
        var questionResources=questions.stream().map(QuestionResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(questionResources);
    }

    @PostMapping
    public ResponseEntity<QuestionResource> createQuestion(@RequestBody CreateQuestionResource resource){
        var createQuestionCommand= CreateQuestionFromResourceAssembler.toCommandFromResource(resource);
        var question = questionCommandService.handle(createQuestionCommand);
        if(question.isEmpty())
            return ResponseEntity.badRequest().build();
        var questionResource = QuestionResourceFromEntityAssembler.toResourceFromEntity(question.get());
        return new ResponseEntity<>(questionResource, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping(value = "/{questionId}")
    public ResponseEntity<QuestionResource> updateQuestion(@PathVariable Long questionId , @RequestBody UpdateQuestionResource resource){
        var updateQuestionCommand = UpdateQuestionCommandFronResourceAssembler.toCommandFromResource(questionId,resource);
        var updateQuestion = questionCommandService.handle(updateQuestionCommand);
        if(updateQuestion.isEmpty())
            return ResponseEntity.badRequest().build();
        var questionResource = QuestionResourceFromEntityAssembler.toResourceFromEntity(updateQuestion.get());
        return ResponseEntity.ok(questionResource);
    }

    @DeleteMapping(value = "/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId){
        var deleteQuestionCommand = new DeleteQuestionCommand(questionId);
        questionCommandService.handle(deleteQuestionCommand);
        return ResponseEntity.ok(Map.of("message", "Question with given id successfully deleted"));
    }
}
