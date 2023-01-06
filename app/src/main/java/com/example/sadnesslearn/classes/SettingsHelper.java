package com.example.sadnesslearn.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class SettingsHelper {
    private static SharedPreferences sadnessSettings;

    public static void changeLocale(String locale_s, Context context) {
        Locale locale = new Locale(locale_s);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void saveLocale(String locale, Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sadnessSettings.edit();
        editor.putString(Constants.APP_PREFERENCES_LANG, locale);
        editor.apply();
    }

    public static String getStringLocaleFromPosition(int position) {
        switch (position) {
            case 1:
                return "en";
            default:
                return "ru";
        }
    }

    public static boolean localeExists(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return sadnessSettings.contains(Constants.APP_PREFERENCES_LANG);
    }

    public static String getStringLocaleFromPreferences(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return sadnessSettings.getString(Constants.APP_PREFERENCES_LANG, "");
    }
}
