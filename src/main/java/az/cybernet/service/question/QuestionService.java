package az.cybernet.service.question;

import az.cybernet.model.view.ConfirmedQuestionRequest;
import az.cybernet.model.view.QuestionRequest;
import az.cybernet.model.view.QuestionResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface QuestionService {
    ResponseEntity<?> save(QuestionRequest questionRequest, HttpServletRequest request);

    ResponseEntity<?> updateUnconfirmedQuestion(Long questionId, QuestionRequest questionRequest, HttpServletRequest request);

    void deleteUnconfirmedQuestion(Long questionId, HttpServletRequest request);

    ResponseEntity<?> findById(Long questionId, HttpServletRequest request);

    ResponseEntity<?> updateConfirmedQuestion(Long questionId, QuestionRequest questionRequest, HttpServletRequest request);

    void deleteConfirmedQuestion(Long questionId, HttpServletRequest request);

    ResponseEntity<?> updateQuestionStatusAndDifficultDegree(Long questionId, ConfirmedQuestionRequest questionRequest, HttpServletRequest request);

    List<QuestionResponse> questionList(int count, HttpServletRequest request);
}
