package com.example.securityquestions.service;

import com.example.securityquestions.entity.SecurityQuestionAnswer;
import com.example.securityquestions.entity.User;
import com.example.securityquestions.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<SecurityQuestionAnswer> storedAnswersForUser = user.getSecurityQuestionAnswers();

        List<String> storedQuestionsForUser = getSecurityQuestionsForUser(user.getUsername());

        if (storedQuestionsForUser.size() != storedAnswersForUser.size()) {
            return false;
        }

        // Compare the provided answers with the stored answers
        for (SecurityQuestionAnswer providedAnswer : securityQuestionAnswers) {
            boolean matchFound = false;
            for (SecurityQuestionAnswer storedAnswer : storedAnswersForUser) {
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

    public List<String> getSecurityQuestionsForUser(String username) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<SecurityQuestionAnswer> securityQuestionAnswers = user.getSecurityQuestionAnswers();
        return securityQuestionAnswers.stream()
                .map(SecurityQuestionAnswer::getQuestion)
                .collect(Collectors.toList());
    }
}
