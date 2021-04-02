package az.cybernet.service.question;

import az.cybernet.mapper.QuestionMapper;
import az.cybernet.model.entity.question.Question;
import az.cybernet.model.enums.RoleName;
import az.cybernet.model.view.ConfirmedQuestionRequest;
import az.cybernet.model.view.QuestionRequest;
import az.cybernet.model.view.QuestionResponse;
import az.cybernet.repository.question.QuestionRepository;
import az.cybernet.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final String jwtHeader;
    private final JwtTokenUtil jwtTokenUtil;

    public QuestionServiceImpl(QuestionRepository questionRepository,
                               @Value("${jwt.header}") String jwtHeader,
                               JwtTokenUtil jwtTokenUtil) {
        this.questionRepository = questionRepository;
        this.jwtHeader = jwtHeader;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(QuestionRequest questionRequest, HttpServletRequest request) {
        try {
            Question question = Question.builder()
                    .content(questionRequest.getContent())
                    .difficulty(questionRequest.getDifficulty())
                    .build();
            return ResponseEntity.ok(questionRepository.save(question));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateUnconfirmedQuestion(Long questionId, QuestionRequest questionRequest, HttpServletRequest request) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            if (Objects.nonNull(questionRequest)) {
                if (Objects.nonNull(questionRequest.getContent())) {
                    if (optionalQuestion.isPresent() && !optionalQuestion.get().isConfirmed()) {
                        Question question = Question.builder()
                                .content(questionRequest.getContent())
                                .difficulty(questionRequest.getDifficulty())
                                .build();
                        return ResponseEntity.ok(questionRepository.save(question));
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this is confirmed question");
                    }

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content is null");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Object is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteUnconfirmedQuestion(Long questionId, HttpServletRequest request) {
        try {
            Optional<Question> question = questionRepository.findById(questionId);
            if (question.isPresent() && !question.get().isConfirmed()) {
                questionRepository.delete(question.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResponseEntity<?> findById(Long questionId, HttpServletRequest request) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            String[] dataFromToken = getUserDataFromHttpServletRequest(request).split("#");
            if (optionalQuestion.isPresent()) {
                if (dataFromToken[1].equals(RoleName.ADMIN.toString())) {
                    return ResponseEntity.ok(QuestionMapper.INSTANCE.mapEntityToView(optionalQuestion.get()));
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user role is not ADMIN");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(" could not find question");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateConfirmedQuestion(Long questionId, QuestionRequest questionRequest, HttpServletRequest request) {
        try {
            Optional<Question> optionalQuestion = questionRepository.findById(questionId);
            String[] dataFromToken = getUserDataFromHttpServletRequest(request).split("#");
            if (dataFromToken[1].equals(RoleName.ADMIN.toString())) {
                if (Objects.nonNull(questionRequest)) {
                    if (Objects.nonNull(questionRequest.getContent())) {
                        if (optionalQuestion.isPresent() && optionalQuestion.get().isConfirmed()) {
                            Question question = optionalQuestion.get();
                            question.setContent(questionRequest.getContent());
                            question.setDifficulty(questionRequest.getDifficulty());
                            return ResponseEntity.ok(questionRepository.save(question));
                        } else {
                            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("this is Unconfirmed question");
                        }

                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content is null");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Object is null");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User role is not ADMIN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteConfirmedQuestion(Long questionId, HttpServletRequest request) {
        try {
            Optional<Question> question = questionRepository.findById(questionId);
            String[] dataFromToken = getUserDataFromHttpServletRequest(request).split("#");
            if (dataFromToken[1].equals(RoleName.ADMIN.toString())) {
                if (question.isPresent() && question.get().isConfirmed()) {
                    questionRepository.delete(question.get());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateQuestionStatusAndDifficultDegree(Long questionId, ConfirmedQuestionRequest questionRequest, HttpServletRequest request) {
        try {
            String[] dataFromToken = getUserDataFromHttpServletRequest(request).split("#");
            if (dataFromToken[1].equals(RoleName.ADMIN.toString())) {
                Optional<Question> optionalQuestion = questionRepository.findById(questionId);
                if (optionalQuestion.isPresent()) {
                    Question question = optionalQuestion.get();
                    question.setConfirmed(true);
                    question.setDifficulty(questionRequest.getDifficulty());
                    return ResponseEntity.ok(questionRepository.save(question));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("question not found");
                }
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user role is not ADMIN");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<QuestionResponse> questionList(int count, HttpServletRequest request) {
        List<Question> questions = new ArrayList<>();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        String[] dataFromToken = getUserDataFromHttpServletRequest(request).split("#");
        if (dataFromToken[1].equals(RoleName.ADMIN.toString())) {
            Map<Integer, List<Question>> listMap = questionRepository.findByConfirmedTrue()
                    .stream().collect(groupingBy(Question::getDifficulty));
            for (var key : listMap.keySet()) {
                if (listMap.get(key).size() < count) {
                    count = listMap.get(key).size();
                }
            }
            for (var key : listMap.keySet()
            ) {
                Collections.shuffle(listMap.get(key));
                questions.addAll(listMap.get(key).stream().limit(count).collect(Collectors.toList()));
            }
            for (Question question : questions
            ) {
                questionResponses.add(QuestionMapper.INSTANCE.mapEntityToView(question));
            }
            return questionResponses;
        } else {
            return Collections.EMPTY_LIST;
        }
    }


    private String getUserDataFromHttpServletRequest(HttpServletRequest request) {
        final String requestHeader = request.getHeader(this.jwtHeader);
        if (Objects.nonNull(requestHeader)
                && requestHeader.startsWith("Bearer ")
                && !requestHeader.contains("null")) {
            String authorizationToken = requestHeader.substring(7);
            return jwtTokenUtil.getUserNameFromToken(authorizationToken);
        } else {
            return null;
        }
    }
}
