package com.example.bioscoopapp.Presentation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.util.Log;

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
            saveLang(String.valueOf(newValue));
            return true;
        });
    }

    public void saveLang(String langCode) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("LanguageKey", langCode).apply();
    }

}