package com.example.reelsescape.model;

public class SupportedApp {
    private final String packageName;
    private final String displayName;
    private boolean enabled;
    private final String targetContentType;

    public SupportedApp(String packageName, String displayName, boolean enabled, String targetContentType) {
        this.packageName = packageName;
        this.displayName = displayName;
        this.enabled = enabled;
        this.targetContentType = targetContentType;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTargetContentType() {
        return targetContentType;
    }
}
