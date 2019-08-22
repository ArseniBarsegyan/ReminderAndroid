package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.user.User;
import com.arsbars.reminderandroid.data.user.UserRepository;

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
        User user = this.userRepository.createUser(this.username, this.password);
        if (user != null) {
            return 1;
        }
        return 0;
    }
}
