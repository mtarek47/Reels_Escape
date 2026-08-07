package com.example.reelsescape.model;

import com.example.reelsescape.constants.AppConstants;

public class AppSettings {
    private boolean masterProtectionEnabled;
    private boolean youtubeEnabled;
    private boolean instagramEnabled;
    private boolean facebookEnabled;
    private String sensitivity;
    private boolean debugMode;
    private boolean onboardingCompleted;

    public AppSettings() {
        this.masterProtectionEnabled = true;
        this.youtubeEnabled = true;
        this.instagramEnabled = true;
        this.facebookEnabled = true;
        this.sensitivity = AppConstants.SENSITIVITY_MEDIUM;
        this.debugMode = false;
        this.onboardingCompleted = false;
    }

    public boolean isMasterProtectionEnabled() {
        return masterProtectionEnabled;
    }

    public void setMasterProtectionEnabled(boolean masterProtectionEnabled) {
        this.masterProtectionEnabled = masterProtectionEnabled;
    }

    public boolean isYoutubeEnabled() {
        return youtubeEnabled;
    }

    public void setYoutubeEnabled(boolean youtubeEnabled) {
        this.youtubeEnabled = youtubeEnabled;
    }

    public boolean isInstagramEnabled() {
        return instagramEnabled;
    }

    public void setInstagramEnabled(boolean instagramEnabled) {
        this.instagramEnabled = instagramEnabled;
    }

    public boolean isFacebookEnabled() {
        return facebookEnabled;
    }

    public void setFacebookEnabled(boolean facebookEnabled) {
        this.facebookEnabled = facebookEnabled;
    }

    public String getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isOnboardingCompleted() {
        return onboardingCompleted;
    }

    public void setOnboardingCompleted(boolean onboardingCompleted) {
        this.onboardingCompleted = onboardingCompleted;
    }

    public int getConfidenceThreshold() {
        if (AppConstants.SENSITIVITY_LOW.equalsIgnoreCase(sensitivity)) {
            return AppConstants.THRESHOLD_LOW;
        } else if (AppConstants.SENSITIVITY_HIGH.equalsIgnoreCase(sensitivity)) {
            return AppConstants.THRESHOLD_HIGH;
        } else {
            return AppConstants.THRESHOLD_MEDIUM;
        }
    }

    public boolean isPackageEnabled(String packageName) {
        if (!masterProtectionEnabled) return false;
        if (AppConstants.PACKAGE_YOUTUBE.equals(packageName)) return youtubeEnabled;
        if (AppConstants.PACKAGE_INSTAGRAM.equals(packageName)) return instagramEnabled;
        if (AppConstants.PACKAGE_FACEBOOK.equals(packageName)) return facebookEnabled;
        return false;
    }
}
