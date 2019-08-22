package com.arsbars.reminderandroid.viewmodels.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.user.UserRepository;
import com.arsbars.reminderandroid.viewmodels.LoginViewModel;

public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private UserRepository userRepository;

    public LoginViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new LoginViewModel(userRepository);
    }
}
