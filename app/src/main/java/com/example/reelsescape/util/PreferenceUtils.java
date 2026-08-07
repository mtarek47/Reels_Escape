package com.example.reelsescape.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.model.AppSettings;

public class PreferenceUtils {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
    }

    public static AppSettings loadSettings(Context context) {
        SharedPreferences prefs = getPrefs(context);
        AppSettings settings = new AppSettings();
        settings.setMasterProtectionEnabled(prefs.getBoolean(AppConstants.KEY_MASTER_PROTECTION, true));
        settings.setYoutubeEnabled(prefs.getBoolean(AppConstants.KEY_YOUTUBE_ENABLED, true));
        settings.setInstagramEnabled(prefs.getBoolean(AppConstants.KEY_INSTAGRAM_ENABLED, true));
        settings.setFacebookEnabled(prefs.getBoolean(AppConstants.KEY_FACEBOOK_ENABLED, true));
        settings.setSensitivity(prefs.getString(AppConstants.KEY_SENSITIVITY, AppConstants.SENSITIVITY_MEDIUM));
        settings.setDebugMode(prefs.getBoolean(AppConstants.KEY_DEBUG_MODE, false));
        settings.setOnboardingCompleted(prefs.getBoolean(AppConstants.KEY_ONBOARDING_COMPLETED, false));
        return settings;
    }

    public static void saveMasterProtection(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_MASTER_PROTECTION, enabled).apply();
    }

    public static void saveYoutubeEnabled(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_YOUTUBE_ENABLED, enabled).apply();
    }

    public static void saveInstagramEnabled(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_INSTAGRAM_ENABLED, enabled).apply();
    }

    public static void saveFacebookEnabled(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_FACEBOOK_ENABLED, enabled).apply();
    }

    public static void saveSensitivity(Context context, String sensitivity) {
        getPrefs(context).edit().putString(AppConstants.KEY_SENSITIVITY, sensitivity).apply();
    }

    public static void saveDebugMode(Context context, boolean enabled) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_DEBUG_MODE, enabled).apply();
    }

    public static void saveOnboardingCompleted(Context context, boolean completed) {
        getPrefs(context).edit().putBoolean(AppConstants.KEY_ONBOARDING_COMPLETED, completed).apply();
    }

    public static void resetSettings(Context context) {
        getPrefs(context).edit().clear().apply();
    }
}
