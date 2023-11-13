package com.microservice.questionservice.controller;

import com.microservice.questionservice.domain.Question;
import com.microservice.questionservice.domain.QuestionWrapper;
import com.microservice.questionservice.domain.Response;
import com.microservice.questionservice.service.QuestionService;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionRestController {

    @Autowired
    QuestionService questionService;
    @GetMapping("allquestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    @GetMapping("category/{category}")
    public List<Question> getAllQuestionsByCategory(@PathVariable String category){
        return questionService.getAllQuestionsByCategory(category);
    }

    @PostMapping("add")
    public Long addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz(@RequestParam(name = "category") String category, @RequestParam(name = "numQn") Integer numQn){
        return questionService.getQuestionForQuiz(category, numQn);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionFromId(@RequestBody List<Long> questionIdList){
        return questionService.getQuestionFromId(questionIdList);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScoreForQuiz(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
}
