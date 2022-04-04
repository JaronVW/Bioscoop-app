package com.example.bioscoopapp.Presentation;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.R;

public class PreferencesActivity extends PreferenceActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        ListPreference languageSelector =
                (ListPreference) getPreferenceScreen().findPreference("LanguageSelector");
        LanguageManager languageManager = new LanguageManager(this);
        languageSelector.setOnPreferenceChangeListener((preference, newValue) -> {
            Log.d(LOG_TAG, "Selected language: " + newValue);
            languageManager.updateResource(String.valueOf(newValue));
            recreate();
            return true;
        });
    }

}