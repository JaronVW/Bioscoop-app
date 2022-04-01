package com.example.bioscoopapp.Logic;

import com.example.bioscoopapp.Domain.SpokenLanguage;

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

    public String getMinutesToText(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes - (hours * 60);
        return hours + " hours and " + remainingMinutes + " minutes.";
    }
}
