package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private boolean isUsingPin;

    public boolean isUsingPin() {
        return isUsingPin;
    }

    public void setUsingPin(boolean usingPin) {
        isUsingPin = usingPin;
    }
}
