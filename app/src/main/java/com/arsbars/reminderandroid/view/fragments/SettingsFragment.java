package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.viewmodels.SettingsViewModel;

public class SettingsFragment extends PreferenceFragmentCompat {
    private MainActivity activity;
    private SettingsViewModel viewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.settings_preferences);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (MainActivity)getActivity();
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        viewModel.setUsingPin(prefs.getBoolean(getResources().getString(R.string.pin_preference),
                false));
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.navigateToRoot(getString(R.string.settings), this);
    }
}
