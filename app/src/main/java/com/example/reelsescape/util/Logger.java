package com.example.reelsescape.util;

import android.util.Log;

public class Logger {
    private static final String TAG = "ReelsEscape";
    private static boolean debugMode = false;

    public static void setDebugMode(boolean enabled) {
        debugMode = enabled;
    }

    public static void d(String subTag, String message) {
        // Always log DetectionEngine for live diagnostics
        if (debugMode || "DetectionEngine".equals(subTag) || "ReelsAccessibilityService".equals(subTag)) {
            Log.d(TAG, "[" + subTag + "] " + message);
        }
    }

    public static void i(String subTag, String message) {
        Log.i(TAG, "[" + subTag + "] " + message);
    }

    public static void w(String subTag, String message) {
        Log.w(TAG, "[" + subTag + "] " + message);
    }

    public static void e(String subTag, String message, Throwable throwable) {
        Log.e(TAG, "[" + subTag + "] " + message, throwable);
    }
}
