package com.microservice.quizservice.repository;

import com.microservice.quizservice.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
