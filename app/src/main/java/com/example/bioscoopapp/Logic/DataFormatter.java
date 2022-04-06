package com.example.bioscoopapp.Logic;

import android.content.Context;

import com.example.bioscoopapp.Domain.SpokenLanguage;
import com.example.bioscoopapp.R;

import java.util.List;

public class DataFormatter {

    public String getFormattedSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        StringBuilder s = new StringBuilder();
        int count = 0;
        for (SpokenLanguage l : spokenLanguages) {
            count++;
            s.append(l.getEnglishName());
            if (count != spokenLanguages.size()) {
                s.append(", ");
            } else {
                s.append(".");
            }
        }
        return s.toString();
    }

    public String getMinutesToText(int minutes, Context context) {
        int hours = minutes / 60;
        int remainingMinutes = minutes - (hours * 60);
        return hours + " " + context.getResources().getString(R.string.hours_and_text) + " " +
                remainingMinutes + " " + context.getResources().getString(R.string.minutes_text) + ".";
    }
}
