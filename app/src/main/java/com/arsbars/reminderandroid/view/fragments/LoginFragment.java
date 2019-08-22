package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.user.UserDbHelper;
import com.arsbars.reminderandroid.data.user.UserRepository;
import com.arsbars.reminderandroid.viewmodels.LoginViewModel;
import com.arsbars.reminderandroid.viewmodels.factory.LoginViewModelFactory;

public class LoginFragment extends Fragment {
    private MainActivity activity;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LoginViewModel loginViewModel = ViewModelProviders
                .of(this, new LoginViewModelFactory(new UserRepository(new UserDbHelper(getContext()))))
                .get(LoginViewModel.class);

        activity = (MainActivity)getActivity();
    }
}
