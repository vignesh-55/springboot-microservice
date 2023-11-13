package com.microservice.questionservice.service;

import com.microservice.questionservice.domain.Question;
import com.microservice.questionservice.domain.QuestionWrapper;
import com.microservice.questionservice.domain.Response;
import com.microservice.questionservice.repository.QuestionRepository;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    Environment environment;

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public List<Question> getAllQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    public Long addQuestion(Question question) {
        return questionRepository.save(question).getId();
    }

    public ResponseEntity<List<Long>> getQuestionForQuiz(String category, Integer numQn) {
        List<Question> questionList = questionRepository.findRandomByCategoryAndLimit(category, numQn);
        List<Long> questionIdList = questionList.stream().map(Question::getId).toList();
        return new ResponseEntity(questionIdList, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(List<Long> questionIdList) {
        System.out.println(environment.getProperty("local.server.port"));
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questionList = new ArrayList<>();
        for(Long id : questionIdList){
            questionList.add(questionRepository.findById(id).get());
        }
        for(Question question : questionList){
            QuestionWrapper questionWrapper = new QuestionWrapper();
            questionWrapper.setId(question.getId());
            questionWrapper.setQuestionTitle(question.getQuestionTitle());
            questionWrapper.setOption1(question.getOption1());
            questionWrapper.setOption2(question.getOption2());
            questionWrapper.setOption3(question.getOption3());
            questionWrapper.setOption4(question.getOption4());
            questionWrappers.add(questionWrapper);
        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer right = 0;
        for(Response response : responses){
            Question question = questionRepository.findById(response.getQuestionId()).get();
            if(response.getUserAnswer().equalsIgnoreCase(question.getRightAnswer())){
                right++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
