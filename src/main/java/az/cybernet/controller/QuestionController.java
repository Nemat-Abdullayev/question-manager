package az.cybernet.controller;

import az.cybernet.model.view.ConfirmedQuestionRequest;
import az.cybernet.model.view.QuestionRequest;
import az.cybernet.model.view.QuestionResponse;
import az.cybernet.service.question.QuestionService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${cbrnt.root.question}")
@Api("endpoint for question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<?> addQuestion(@Valid @RequestBody QuestionRequest questionRequest, HttpServletRequest request) {
        return ResponseEntity.ok(questionService.save(questionRequest, request));
    }

    @PatchMapping("/unconfirmed/{questionId}")
    public ResponseEntity<?> updateUnconfirmedQuestion(@PathVariable("questionId") Long questionId,
                                                       @RequestBody QuestionRequest questionRequest, HttpServletRequest servletRequest) {
        return questionService.updateUnconfirmedQuestion(questionId, questionRequest, servletRequest);
    }

    @DeleteMapping("/unconfirmed/{questionId}")
    public void deleteUnconfirmedQuestion(@PathVariable("questionId") Long questionId, HttpServletRequest request) {
        questionService.deleteUnconfirmedQuestion(questionId, request);
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable("questionId") Long questionId, HttpServletRequest request) {
        return questionService.findById(questionId, request);
    }

    @PatchMapping("/confirmed/{questionId}")
    public ResponseEntity<?> updateConfirmedQuestion(@PathVariable("questionId") Long questionId,
                                                     @RequestBody QuestionRequest questionRequest, HttpServletRequest servletRequest) {
        return questionService.updateConfirmedQuestion(questionId, questionRequest, servletRequest);
    }

    @DeleteMapping("/confirmed/{questionId}")
    public void deleteConfirmedQuestion(@PathVariable("questionId") Long questionId, HttpServletRequest request) {
        questionService.deleteConfirmedQuestion(questionId, request);
    }

    @PatchMapping("/{questionId}/confirm")
    public ResponseEntity<?> confirmedQuestion(@PathVariable("questionId") Long questionId,
                                               @RequestBody ConfirmedQuestionRequest confirmedQuestionRequest,
                                               HttpServletRequest request) {
        return questionService.updateQuestionStatusAndDifficultDegree(questionId, confirmedQuestionRequest, request);
    }

    @GetMapping("/{count}/test-bank")
    public List<QuestionResponse> questionLis(@PathVariable("count") int count,
                                              HttpServletRequest request) {
        return questionService.questionList(count, request);
    }
}
