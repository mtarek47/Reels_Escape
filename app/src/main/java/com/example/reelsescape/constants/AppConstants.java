package com.example.reelsescape.constants;

public class AppConstants {
    // Packages
    public static final String PACKAGE_YOUTUBE = "com.google.android.youtube";
    public static final String PACKAGE_INSTAGRAM = "com.instagram.android";
    public static final String PACKAGE_FACEBOOK = "com.facebook.katana";

    // Sensitivity thresholds
    public static final int THRESHOLD_LOW = 85;
    public static final int THRESHOLD_MEDIUM = 70;
    public static final int THRESHOLD_HIGH = 55;

    // Default timings (in milliseconds)
    public static final long DEFAULT_COOLDOWN_MS = 2000L;
    public static final long DEFAULT_DEBOUNCE_MS = 350L;

    // Content types
    public static final String CONTENT_TYPE_SHORTS = "Shorts";
    public static final String CONTENT_TYPE_REELS = "Reels";

    // App Names
    public static final String APP_NAME_YOUTUBE = "YouTube";
    public static final String APP_NAME_INSTAGRAM = "Instagram";
    public static final String APP_NAME_FACEBOOK = "Facebook";

    // SharedPreferences Keys
    public static final String PREF_NAME = "reels_escape_prefs";
    public static final String KEY_MASTER_PROTECTION = "master_protection";
    public static final String KEY_YOUTUBE_ENABLED = "youtube_enabled";
    public static final String KEY_INSTAGRAM_ENABLED = "instagram_enabled";
    public static final String KEY_FACEBOOK_ENABLED = "facebook_enabled";
    public static final String KEY_SENSITIVITY = "sensitivity";
    public static final String KEY_DEBUG_MODE = "debug_mode";
    public static final String KEY_ONBOARDING_COMPLETED = "onboarding_completed";

    // Sensitivity Levels
    public static final String SENSITIVITY_LOW = "Low";
    public static final String SENSITIVITY_MEDIUM = "Medium";
    public static final String SENSITIVITY_HIGH = "High";
}
