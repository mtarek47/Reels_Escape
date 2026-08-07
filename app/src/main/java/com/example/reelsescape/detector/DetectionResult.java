package com.example.reelsescape.detector;

public class DetectionResult {
    private final boolean detected;
    private final int confidence;
    private final String packageName;
    private final String contentType;
    private final String matchedRule;
    private final long timestamp;
    private final String debugSummary;

    public DetectionResult(boolean detected, int confidence, String packageName, String contentType, String matchedRule, String debugSummary) {
        this.detected = detected;
        this.confidence = confidence;
        this.packageName = packageName;
        this.contentType = contentType;
        this.matchedRule = matchedRule;
        this.timestamp = System.currentTimeMillis();
        this.debugSummary = debugSummary;
    }

    public static DetectionResult notDetected(String packageName, String reason) {
        return new DetectionResult(false, 0, packageName, "", "NONE", reason);
    }

    public boolean isDetected() {
        return detected;
    }

    public int getConfidence() {
        return confidence;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getMatchedRule() {
        return matchedRule;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDebugSummary() {
        return debugSummary;
    }

    @Override
    public String toString() {
        return "DetectionResult{" +
                "detected=" + detected +
                ", confidence=" + confidence +
                ", pkg='" + packageName + '\'' +
                ", rule='" + matchedRule + '\'' +
                '}';
    }
}
