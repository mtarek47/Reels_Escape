package com.example.reelsescape.detector;

public class DetectionRule {
    public static final String PACKAGE_MATCH = "PACKAGE_MATCH";
    public static final String SHORTS_KEYWORD = "SHORTS_KEYWORD";
    public static final String REELS_KEYWORD = "REELS_KEYWORD";
    public static final String KNOWN_RESOURCE_ID = "KNOWN_RESOURCE_ID";
    public static final String CONTENT_DESC_MATCH = "CONTENT_DESC_MATCH";
    public static final String SHORT_VIDEO_ACTION_BUTTONS = "SHORT_VIDEO_ACTION_BUTTONS";
    public static final String VERTICAL_REEL_LAYOUT = "VERTICAL_REEL_LAYOUT";
    public static final String AUDIO_PIVOT_MATCH = "AUDIO_PIVOT_MATCH";

    // Score weights
    public static final int SCORE_PACKAGE_MATCH = 20;
    public static final int SCORE_KEYWORD_MATCH = 30;
    public static final int SCORE_RESOURCE_ID = 20;
    public static final int SCORE_CONTENT_DESC = 20;
    public static final int SCORE_ACTION_BUTTONS = 20;
    public static final int SCORE_VERTICAL_LAYOUT = 10;
}
