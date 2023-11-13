package com.microservice.quizservice.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

//    @ManyToMany
//    private List<Question> questions;
    @ElementCollection
    private List<Long> questions;
}
