package com.example.sadnesslearn.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.example.sadnesslearn.R;
import com.example.sadnesslearn.classes.languages.LocaleLanguages;

import java.util.Locale;
import java.util.Objects;

public class SettingsHelper {
    private static SharedPreferences sadnessSettings;
    private static String oldLocale = null;
    private static Resources.Theme oldTheme = null;

    public static void changeLocale(String locale_s, Context context) {
        Locale locale = new Locale(locale_s);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void saveLocale(String locale, Context context) {
        oldLocale = context.getResources().getConfiguration().locale.toString();
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sadnessSettings.edit();
        editor.putString(Constants.APP_PREFERENCES_LANG, locale);
        editor.apply();
    }

    public static LocaleLanguages getLocaleFromPosition(int position) {
        switch (position) {
            case 1:
                return LocaleLanguages.ENGLISH;
            default:
                return LocaleLanguages.RUSSIAN;
        }
    }

    public static boolean localeExists(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return sadnessSettings.contains(Constants.APP_PREFERENCES_LANG);
    }

    public static LocaleLanguages getLocaleFromPreferences(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return LocaleLanguages.getLocale(
                Objects.requireNonNull(sadnessSettings.getString(Constants.APP_PREFERENCES_LANG,
                        String.valueOf(context.getResources().getConfiguration().locale))));
    }

    public static void saveTheme(Context context, int themeId) {
        oldTheme = context.getTheme();
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sadnessSettings.edit();
        editor.putInt(Constants.APP_PREFERENCES_THEME, themeId);
        editor.apply();
    }

    public static boolean themeExists(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return sadnessSettings.contains(Constants.APP_PREFERENCES_THEME);
    }

    public static int getThemeFromPrefs(Context context) {
        sadnessSettings = context.getSharedPreferences(Constants.APP_PREFERENCES, Context.MODE_PRIVATE);
        return sadnessSettings.getInt(Constants.APP_PREFERENCES_THEME, 0);
    }

    public static int getThemeFromColor(Context context, int color) {

        if (color == context.getResources().getColor(R.color.primary)) {
            return R.style.Theme_SadnessLearn_Pink;
        }
        else if (color == context.getResources().getColor(R.color.primaryPurple)) {
            return R.style.Theme_SadnessLearn_Purple;
        }
        else if (color == context.getResources().getColor(R.color.primaryDeepPurple)) {
            return R.style.Theme_SadnessLearn_DeepPurple;
        }
        else if (color == context.getResources().getColor(R.color.primaryIndigo)) {
            return R.style.Theme_SadnessLearn_Indigo;
        }
        else if (color == context.getResources().getColor(R.color.primaryBlue)) {
            return R.style.Theme_SadnessLearn_Blue;
        }
        else if (color == context.getResources().getColor(R.color.primaryCyan)) {
            return R.style.Theme_SadnessLearn_Cyan;
        }
        else if (color == context.getResources().getColor(R.color.primaryTeal)) {
            return R.style.Theme_SadnessLearn_Teal;
        }
        else if (color == context.getResources().getColor(R.color.primaryBlueGrey)) {
            return R.style.Theme_SadnessLearn_BlueGrey;
        }
        else if (color == context.getResources().getColor(R.color.primaryGreen)) {
            return R.style.Theme_SadnessLearn_Green;
        }
        else if (color == context.getResources().getColor(R.color.primaryLime)) {
            return R.style.Theme_SadnessLearn_Lime;
        }
        else if (color == context.getResources().getColor(R.color.primaryYellow)) {
            return R.style.Theme_SadnessLearn_Yellow;
        }
        else if (color == context.getResources().getColor(R.color.primaryOrange)) {
            return R.style.Theme_SadnessLearn_Orange;
        }
        else if (color == context.getResources().getColor(R.color.primaryDeepOrange)) {
            return R.style.Theme_SadnessLearn_DeepOrange;
        }
        else {
            return R.style.Theme_SadnessLearn_Red;
        }
    }

    public static boolean settingsChanged(Context context) {
        if (oldLocale != null && !oldLocale.equals(getLocaleFromPreferences(context).getTitle())) {
            oldLocale = null;
            return true;
        }

        if (oldTheme != null && oldTheme != context.getTheme()) {
            oldTheme = null;
            return true;
        }

        return false;
    }
}
