package com.example.bioscoopapp.Logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.Preference;

import java.util.Locale;

public class LanguageManager {
    private final Context context;
    private final SharedPreferences sharedPreferences;

    public LanguageManager(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences("Language", Context.MODE_PRIVATE);
    }

    public void updateResource(String langCode) {
        Locale locale = new Locale(langCode);
        Locale.setDefault(locale);
        Resources resources = this.context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        setLang(langCode);
    }

    public void setLang(String langCode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", langCode);
        editor.apply();
    }
}
