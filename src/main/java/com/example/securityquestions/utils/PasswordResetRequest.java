package com.example.securityquestions.utils;

import com.example.securityquestions.entity.SecurityQuestionAnswer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PasswordResetRequest {
    private String username;
    private List<SecurityQuestionAnswer> securityQuestionAnswers;
    private String newPassword;
}
