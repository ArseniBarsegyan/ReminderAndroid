package com.arsbars.reminderandroid;

import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.arsbars.reminderandroid.view.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    private void InitiateView() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ConstraintLayout rootLayout = (ConstraintLayout)findViewById(R.id.loginRootLayout);
        fragmentTransaction.replace(R.id.root_layout, LoginFragment.newInstance());
        fragmentTransaction.commit();
    }
}
