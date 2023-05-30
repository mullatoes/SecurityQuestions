package com.example.securityquestions.service;

import com.example.securityquestions.entity.SecurityQuestionAnswer;
import com.example.securityquestions.entity.User;
import com.example.securityquestions.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }
    public void registerUser(
            String username, String password,
            List<SecurityQuestionAnswer> securityQuestionAnswers) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSecurityQuestionAnswers(securityQuestionAnswers);

        userRepository.save(user);
    }

    public void resetPassword(
            String username,
            List<SecurityQuestionAnswer> securityQuestionAnswers,
            String newPassword) {

        // Retrieve the user based on the username
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Validate the security question answers
        boolean answersValid = validateSecurityQuestionAnswers(user, securityQuestionAnswers);
        if (!answersValid) {
            throw new RuntimeException("Invalid security question answers");
        }

        // Set the new password and save the user
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    private boolean validateSecurityQuestionAnswers(User user, List<SecurityQuestionAnswer> securityQuestionAnswers) {
        List<SecurityQuestionAnswer> storedAnswers = user.getSecurityQuestionAnswers();
        if (securityQuestionAnswers.size() != storedAnswers.size()) {
            return false;
        }

        // Compare the provided answers with the stored answers
        for (SecurityQuestionAnswer providedAnswer : securityQuestionAnswers) {
            boolean matchFound = false;
            for (SecurityQuestionAnswer storedAnswer : storedAnswers) {
                if (providedAnswer.getQuestion().equals(storedAnswer.getQuestion())
                        && providedAnswer.getAnswer().equals(storedAnswer.getAnswer())) {
                    matchFound = true;
                    break;
                }
            }
            if (!matchFound) {
                return false;
            }
        }

        return true;
    }
}
