package com.microservice.quizservice.service;

import com.microservice.quizservice.domain.Question;
import com.microservice.quizservice.domain.QuestionWrapper;
import com.microservice.quizservice.domain.Quiz;
import com.microservice.quizservice.domain.Response;
import com.microservice.quizservice.feign.QuizInterface;
import com.microservice.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

//    @Autowired
    private final QuizInterface quizInterface;
//    @Autowired
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizInterface quizInterface, QuizRepository quizRepository){
        this.quizInterface = quizInterface;
        this.quizRepository = quizRepository;
    }

    public ResponseEntity<String> createQuiz(String category, int numQn, String title){
        Quiz quiz = new Quiz();
//        List<Integer> questionList = questionRepository.findRandomByCategoryAndLimit(category, numQn);
        List<Long> questionList = quizInterface.getQuestionsForQuiz(category, numQn).getBody();
        quiz.setTitle(title);
        quiz.setQuestions(questionList);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Quiz Created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
        Optional<Quiz> quizOptional = quizRepository.findById(id);
        List<Long> questionList = quizOptional.get().getQuestions();
        ResponseEntity<List<QuestionWrapper>> questionWrappers = quizInterface.getQuestionsFromId(questionList);
//        for(Question question : questionList){
//            QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
//            questionWrappers.add(questionWrapper);
//        }
        return questionWrappers;
    }

    public ResponseEntity<Integer> submitQuiz(Long quizId, List<Response> responses) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
//        List<Question> questionList = quizOptional.get().getQuestions();
        Integer right = 0, i = 0;
//        for(Response response : responses){
//            if(response.getUserAnswer().equalsIgnoreCase(questionList.get(i).getRightAnswer())){
//                right++;
//            }
//            i++;
//        }
        return new ResponseEntity<>(right, HttpStatus.ACCEPTED);
    }
}
