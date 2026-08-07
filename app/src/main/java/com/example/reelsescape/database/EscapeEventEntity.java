package com.example.reelsescape.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "escape_events")
public class EscapeEventEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "package_name")
    private String packageName;

    @ColumnInfo(name = "app_name")
    private String appName;

    @ColumnInfo(name = "content_type")
    private String contentType;

    @ColumnInfo(name = "confidence_score")
    private int confidenceScore;

    @ColumnInfo(name = "detection_rule")
    private String detectionRule;

    public EscapeEventEntity(long timestamp, String packageName, String appName, String contentType, int confidenceScore, String detectionRule) {
        this.timestamp = timestamp;
        this.packageName = packageName;
        this.appName = appName;
        this.contentType = contentType;
        this.confidenceScore = confidenceScore;
        this.detectionRule = detectionRule;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getConfidenceScore() {
        return confidenceScore;
    }

    public void setConfidenceScore(int confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public String getDetectionRule() {
        return detectionRule;
    }

    public void setDetectionRule(String detectionRule) {
        this.detectionRule = detectionRule;
    }
}
