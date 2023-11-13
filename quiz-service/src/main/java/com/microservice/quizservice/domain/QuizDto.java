package com.microservice.quizservice.domain;

import lombok.Data;

@Data
public class QuizDto {
    private String category;
    private int numQn;
    private String title;
}
