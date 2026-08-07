package com.example.reelsescape.repository;

import android.content.Context;

import com.example.reelsescape.model.AppSettings;
import com.example.reelsescape.util.Logger;
import com.example.reelsescape.util.PreferenceUtils;

public class SettingsRepository {

    private final Context context;

    public SettingsRepository(Context context) {
        this.context = context.getApplicationContext();
    }

    public AppSettings getSettings() {
        return PreferenceUtils.loadSettings(context);
    }

    public void setMasterProtection(boolean enabled) {
        PreferenceUtils.saveMasterProtection(context, enabled);
    }

    public void setYoutubeEnabled(boolean enabled) {
        PreferenceUtils.saveYoutubeEnabled(context, enabled);
    }

    public void setInstagramEnabled(boolean enabled) {
        PreferenceUtils.saveInstagramEnabled(context, enabled);
    }

    public void setFacebookEnabled(boolean enabled) {
        PreferenceUtils.saveFacebookEnabled(context, enabled);
    }

    public void setSensitivity(String sensitivity) {
        PreferenceUtils.saveSensitivity(context, sensitivity);
    }

    public void setDebugMode(boolean enabled) {
        PreferenceUtils.saveDebugMode(context, enabled);
        Logger.setDebugMode(enabled);
    }

    public void setOnboardingCompleted(boolean completed) {
        PreferenceUtils.saveOnboardingCompleted(context, completed);
    }

    public void resetSettings() {
        PreferenceUtils.resetSettings(context);
    }
}
