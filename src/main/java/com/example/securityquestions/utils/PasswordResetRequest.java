package com.example.securityquestions.utils;

import com.example.securityquestions.entity.SecurityQuestion;
import com.example.securityquestions.entity.SecurityQuestionAnswer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PasswordResetRequest {
    private String username;
    private List<SecurityQuestion> securityQuestions;
    private List<SecurityQuestionAnswer> securityQuestionAnswers;
    private String newPassword;
}
