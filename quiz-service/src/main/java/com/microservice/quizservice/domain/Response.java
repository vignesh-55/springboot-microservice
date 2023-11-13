package com.microservice.quizservice.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private Long questionId;
    private String userAnswer;
}
