package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.arsbars.reminderandroid.data.user.User;
import com.arsbars.reminderandroid.data.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginViewModel extends ViewModel {
    private String username;
    private String password;
    private String confirmPassword;

    private UserRepository userRepository;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int createNewUser() {
        try {
            User existingUser = this.userRepository.getUserByName(getUsername());
            if (existingUser != null) {
                return 0;
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            User user = this.userRepository.createUser(this.username, hash);
            if (user != null) {
                return 1;
            }
            return 0;
        } catch (NoSuchAlgorithmException e) {
            Log.d("Algorithm", e.getMessage(),e);
        }
        return 0;
    }

    public boolean login() {
        boolean result = false;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            String hashedString = new String(hash);
            User user = this.userRepository.getUserByName(getUsername());
            if (user != null) {
                String userPasswordHashedString = new String(user.getPassword());
                result = hashedString.equals(userPasswordHashedString);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.d("Algorithm", e.getMessage(), e);
        }
        return result;
    }
}
