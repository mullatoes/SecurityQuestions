package com.example.securityquestions.controller;

import com.example.securityquestions.entity.SecurityQuestion;
import com.example.securityquestions.repository.SecurityQuestionRepository;
import com.example.securityquestions.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/security-questions")
public class SecurityQuestionController {

    private final UserService service;
    private final SecurityQuestionRepository repository;

    @Autowired
    public SecurityQuestionController(UserService service, SecurityQuestionRepository repository){
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/getAllQuestions/{username}")
    public List<String> getAllQuestions(@PathVariable String username){
       return service.getSecurityQuestionsForUser(username);
    }

    @PostMapping
    public ResponseEntity<SecurityQuestion> createSecurityQuestion(@RequestBody SecurityQuestion securityQuestion) {
        SecurityQuestion createdQuestion = repository.save(securityQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestion);
    }
}
