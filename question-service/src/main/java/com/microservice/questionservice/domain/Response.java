package com.microservice.questionservice.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private Long questionId;
    private String userAnswer;
}
