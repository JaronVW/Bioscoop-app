package com.example.bioscoopapp.Presentation;

import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.bioscoopapp.Logic.LanguageManager;
import com.example.bioscoopapp.R;

import org.intellij.lang.annotations.Language;

public class PreferencesActivity extends PreferenceActivity {

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName() + " DEBUG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Retrieving language from previous session...
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        String langCode = sharedPreferences.getString("LanguageKey", "No previous language.");
        Log.d(LOG_TAG, "Previously selected language: " + langCode);
        LanguageManager languageManager = new LanguageManager(this);
        languageManager.updateResource(String.valueOf(langCode));

        Log.d(LOG_TAG, "Preferences activity opened.");

        //Adding preferences to the screen...
        addPreferencesFromResource(R.xml.preferences);

        //Creating a language selector and giving it functionality...
        ListPreference languageSelector =
                (ListPreference) getPreferenceScreen().findPreference("LanguageSelector");
        languageSelector.setOnPreferenceChangeListener((preference, newValue) -> {
            Log.d(LOG_TAG, "Selected language: " + newValue);
            languageManager.updateResource(String.valueOf(newValue));
            recreate();
            saveLang(String.valueOf(newValue));
            return true;
        });

        SwitchPreference darkModeSwitch = (SwitchPreference) findPreference("DarkModeSwitch");
        boolean isDark = sharedPreferences.getBoolean("IsDark", false);
        if (isDark) {
            darkModeSwitch.setChecked(true);
        }
        darkModeSwitch.setOnPreferenceChangeListener((preference, o) -> {
            //Checking the state of the switch and applying the opposite...
            darkModeSwitch.setChecked(!darkModeSwitch.isChecked());

            //Saving the state of the switch...
            saveMode(darkModeSwitch.isChecked());

            //If the switch is checked, turn on dark mode. Otherwise, turn it off.
            if (darkModeSwitch.isChecked()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            return false;
        });
    }

    public void saveLang(String langCode) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("LanguageKey", langCode).apply();
    }

    public void saveMode(boolean darkMode) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(
                "com.example.app", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("IsDark", darkMode).apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}