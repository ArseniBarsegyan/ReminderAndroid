package com.arsbars.reminderandroid.services;

import android.support.v4.app.Fragment;

public interface FragmentNavigationService {
    void navigateToRoot(String title, Fragment fragment);
    void push(String title, Fragment fragment);
}
