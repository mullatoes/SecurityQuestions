package com.example.securityquestions.controller;

import com.example.securityquestions.service.UserService;
import com.example.securityquestions.utils.PasswordResetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reset-password")
public class PasswordResetController {
    private final UserService userService;

    @Autowired
    public PasswordResetController(UserService service){
        this.userService = service;
    }

    @PostMapping
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        try {
            userService.resetPassword(request.getUsername(), request.getSecurityQuestionAnswers(), request.getNewPassword());
            return ResponseEntity.ok("Password reset successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password");
        }
    }

}
