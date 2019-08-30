package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arsbars.reminderandroid.LoginActivity;
import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.base.DatabaseHelper;
import com.arsbars.reminderandroid.data.user.UserRepository;
import com.arsbars.reminderandroid.viewmodels.LoginViewModel;
import com.arsbars.reminderandroid.viewmodels.factory.LoginViewModelFactory;

public class LoginFragment extends Fragment {
    private LoginActivity activity;
    private TextView titleTextView;
    private EditText userNameEntry;
    private EditText passwordEntry;
    private EditText confirmPasswordEntry;
    private Button loginRegisterButton;
    private Button registerLinkButton;
    private boolean isLoginMode;
    private LoginViewModel loginViewModel;

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
        this.loginViewModel = ViewModelProviders
                .of(this, new LoginViewModelFactory(new UserRepository(
                        new DatabaseHelper(getContext()))))
                .get(LoginViewModel.class);

        activity = (LoginActivity)getActivity();

        initializeControls();
        subscribeControls();
    }

    private void initializeControls() {
        this.titleTextView = activity.findViewById(R.id.loginRegisterTitle);
        this.userNameEntry = activity.findViewById(R.id.usernameEntry);
        this.passwordEntry = activity.findViewById(R.id.passwordEntry);
        this.confirmPasswordEntry = activity.findViewById(R.id.confirmPasswordEntry);
        this.loginRegisterButton = activity.findViewById(R.id.loginRegisterButton);
        this.registerLinkButton = activity.findViewById(R.id.registerLinkButton);
    }

    private void subscribeControls() {
        this.registerLinkButton.setOnClickListener(v -> {
            if (this.isLoginMode) {
                this.titleTextView.setText(getString(R.string.login));
                this.confirmPasswordEntry.setVisibility(View.INVISIBLE);
                this.loginRegisterButton.setText(getString(R.string.login));
                this.registerLinkButton.setText(getString(R.string.register));
            } else {
                this.titleTextView.setText(getString(R.string.register));
                this.confirmPasswordEntry.setVisibility(View.VISIBLE);
                this.loginRegisterButton.setText(getString(R.string.register));
                this.registerLinkButton.setText(getString(R.string.login));
            }
            this.isLoginMode = !this.isLoginMode;
        });
        this.loginRegisterButton.setOnClickListener(v -> {
            this.loginViewModel.setUsername(this.userNameEntry.getText().toString());
            this.loginViewModel.setPassword(this.passwordEntry.getText().toString());
            this.loginViewModel.setConfirmPassword(this.confirmPasswordEntry
                    .getText()
                    .toString());

            if (this.isLoginMode) {
                Toast.makeText(getContext(),"Register", Toast.LENGTH_SHORT).show();
                this.loginViewModel.createNewUser();
            } else {
                if (this.loginViewModel.login()) {
                    Toast.makeText(getContext(),"Login successful", Toast.LENGTH_SHORT)
                            .show();

                    SharedPreferences prefs = PreferenceManager
                            .getDefaultSharedPreferences(getContext());
                    String currentUsername = prefs.getString(getResources()
                                    .getString(R.string.user_name_preference), "");

                    if (currentUsername.equals("")) {
                        SharedPreferences.Editor editor = prefs.edit()
                                .putString(getResources()
                                        .getString(R.string.user_name_preference),
                                this.loginViewModel.getUsername());
                        editor.apply();
                    }
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();
//                    activity.overridePendingTransition(R.anim.scale_in_from_center,
//                            R.anim.scale_out_from_center);
                } else {
                    Toast.makeText(getContext(),"No such user", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }
}
