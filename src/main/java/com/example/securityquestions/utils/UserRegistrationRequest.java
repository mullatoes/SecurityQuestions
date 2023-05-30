package com.example.securityquestions.utils;

import com.example.securityquestions.entity.SecurityQuestionAnswer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private List<SecurityQuestionAnswer> securityQuestionAnswers;
}
