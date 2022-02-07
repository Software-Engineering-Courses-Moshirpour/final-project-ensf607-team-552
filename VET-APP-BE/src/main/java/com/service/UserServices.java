package com.service;

import com.jwt.response.MessageResponse;
import com.model.User;
import com.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public

@Service
@Transactional
class UserServices {

    @Autowired
    private UserDao userRepo;


    public ResponseEntity<?> updateResetPasswordToken(String token, String email) {
        User user = userRepo.findByEmail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            userRepo.save(user);
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(new MessageResponse("password updated successfully!"));
    }

    public User getByResetPasswordToken(String token) {
        return userRepo.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);

        user.setResetPasswordToken(null);
        userRepo.save(user);
    }
}
