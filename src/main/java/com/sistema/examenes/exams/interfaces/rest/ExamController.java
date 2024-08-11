package com.sistema.examenes.exams.interfaces.rest;


import com.sistema.examenes.exams.domain.model.commands.DeleteExamCommand;
import com.sistema.examenes.exams.domain.model.queries.GetAllExamsQuery;
import com.sistema.examenes.exams.domain.model.queries.GetExamByIdQuery;
import com.sistema.examenes.exams.domain.services.ExamCommandService;
import com.sistema.examenes.exams.domain.services.ExamQueryService;
import com.sistema.examenes.exams.interfaces.rest.resources.CreateExamResource;
import com.sistema.examenes.exams.interfaces.rest.resources.ExamResource;
import com.sistema.examenes.exams.interfaces.rest.transform.CreateExamFromResourceAssembler;
import com.sistema.examenes.exams.interfaces.rest.transform.ExamResourceFromEntityAssembler;
import com.sistema.examenes.exams.interfaces.rest.transform.UpdateExamCommandFromResourceAssembler;
import com.sistema.examenes.exams.interfaces.rest.resources.UpdateExamResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/exams")
@Tag(name = "Exams", description = "Exam Management Endpoints")
public class ExamController {

    private final ExamQueryService examQueryService;
    private final ExamCommandService examCommandService;

    public ExamController(ExamQueryService examQueryService, ExamCommandService examCommandService) {
        this.examQueryService = examQueryService;
        this.examCommandService = examCommandService;
    }

    @GetMapping
    public ResponseEntity<List<ExamResource>> getAllExams(){
        var getAllExamsQuery = new GetAllExamsQuery();
        var exams = examQueryService.handle(getAllExamsQuery);
        var examResources=exams.stream().map(ExamResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(examResources);
    }

    @GetMapping(value = "/{examId}")
    public ResponseEntity<ExamResource> getExamById(@PathVariable Long examId){
        var getExamByIdQuery = new GetExamByIdQuery(examId);
        var exam = examQueryService.handle(getExamByIdQuery);
        if(exam.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        var examResource = ExamResourceFromEntityAssembler.toResourceFromEntity(exam.get());
        return ResponseEntity.ok(examResource);
    }

    @PostMapping
    public ResponseEntity<ExamResource> createExam(@RequestBody CreateExamResource resource){
        var createExamCommand = CreateExamFromResourceAssembler.toCommandFromResource(resource);
        var exam = examCommandService.handle(createExamCommand);
        if(exam.isEmpty())
            return ResponseEntity.badRequest().build();
        var examResource = ExamResourceFromEntityAssembler.toResourceFromEntity(exam.get());
        return new ResponseEntity<>(examResource, org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping(value = "/{examId}")
    public ResponseEntity<ExamResource> updateExam(@PathVariable Long examId , @RequestBody UpdateExamResource resource){
        var updateExamCommand= UpdateExamCommandFromResourceAssembler.toCommandFromResource(examId, resource);
        var updateExam= examCommandService.handle(updateExamCommand);
        if(updateExam.isEmpty())
            return ResponseEntity.badRequest().build();
        var examResource = ExamResourceFromEntityAssembler.toResourceFromEntity(updateExam.get());
        return ResponseEntity.ok(examResource);
    }

    @DeleteMapping(value = "/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable Long examId){
        var deleteExamCommand = new DeleteExamCommand(examId);
        examCommandService.handle(deleteExamCommand);
        return ResponseEntity.ok(Map.of("message", "Question with given id successfully deleted"));
    }




}
