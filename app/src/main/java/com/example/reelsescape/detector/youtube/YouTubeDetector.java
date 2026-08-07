package com.example.reelsescape.detector.youtube;

import android.view.accessibility.AccessibilityNodeInfo;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.detector.ContentDetector;
import com.example.reelsescape.detector.DetectionResult;
import com.example.reelsescape.detector.DetectionRule;

import java.util.ArrayList;
import java.util.List;

public class YouTubeDetector implements ContentDetector {

    @Override
    public DetectionResult detect(AccessibilityNodeInfo rootNode, String packageName, int confidenceThreshold) {
        if (rootNode == null || !AppConstants.PACKAGE_YOUTUBE.equals(packageName)) {
            return DetectionResult.notDetected(packageName, "Root null or package mismatch");
        }

        List<String> visibleTexts = new ArrayList<>();
        List<String> contentDescs = new ArrayList<>();
        List<String> viewIds = new ArrayList<>();

        traverseNode(rootNode, visibleTexts, contentDescs, viewIds, 0);

        // False Positive Protection: Check if user is on Home tab or Search tab with a Shorts shelf
        boolean hasHomeNavigation = false;
        boolean hasShortsShelfOnly = false;

        for (String text : visibleTexts) {
            String lower = text.toLowerCase();
            if (lower.equals("home") || lower.equals("subscriptions") || lower.equals("library")) {
                hasHomeNavigation = true;
            }
        }

        int score = DetectionRule.SCORE_PACKAGE_MATCH; // 20
        List<String> matchedRules = new ArrayList<>();
        matchedRules.add(DetectionRule.PACKAGE_MATCH);

        boolean foundShortsKeyword = false;
        boolean foundActionButtons = false;
        boolean foundResourceId = false;
        boolean foundContentDesc = false;

        for (String text : visibleTexts) {
            String lower = text.toLowerCase().trim();
            if (lower.contains("shorts") || lower.equals("short videos")) {
                foundShortsKeyword = true;
            }
            if (lower.contains("remix") || lower.contains("dislike") || lower.contains("use this sound") || lower.contains("sound pivot")) {
                foundActionButtons = true;
            }
        }

        for (String desc : contentDescs) {
            String lower = desc.toLowerCase().trim();
            if (lower.contains("shorts") || lower.contains("reel player")) {
                foundContentDesc = true;
            }
            if (lower.contains("dislike this short") || lower.contains("remix this short") || lower.contains("sound details")) {
                foundActionButtons = true;
            }
        }

        for (String viewId : viewIds) {
            String lower = viewId.toLowerCase();
            if (lower.contains("shorts_player") || lower.contains("reel_player") || lower.contains("reel_container") || lower.contains("shorts_container")) {
                foundResourceId = true;
            }
        }

        // Apply scores
        if (foundShortsKeyword) {
            score += DetectionRule.SCORE_KEYWORD_MATCH;
            matchedRules.add(DetectionRule.SHORTS_KEYWORD);
        }

        if (foundResourceId) {
            score += DetectionRule.SCORE_RESOURCE_ID;
            matchedRules.add(DetectionRule.KNOWN_RESOURCE_ID);
        }

        if (foundActionButtons) {
            score += DetectionRule.SCORE_ACTION_BUTTONS;
            matchedRules.add(DetectionRule.SHORT_VIDEO_ACTION_BUTTONS);
        }

        if (foundContentDesc) {
            score += DetectionRule.SCORE_CONTENT_DESC;
            matchedRules.add(DetectionRule.CONTENT_DESC_MATCH);
        }

        // FALSE POSITIVE GUARD: If home navigation bar is visible and action buttons are absent,
        // user is browsing YouTube Home feed where a "Shorts" shelf banner is present.
        if (hasHomeNavigation && !foundActionButtons && !foundResourceId) {
            score = Math.min(score, 40); // Cap below default threshold (70)
        }

        boolean isDetected = score >= confidenceThreshold;
        String rulesSummary = String.join(", ", matchedRules);
        String debugInfo = "YouTube score=" + score + " threshold=" + confidenceThreshold + " rules=[" + rulesSummary + "]";

        if (isDetected) {
            return new DetectionResult(true, score, packageName, AppConstants.CONTENT_TYPE_SHORTS, rulesSummary, debugInfo);
        } else {
            return new DetectionResult(false, score, packageName, AppConstants.CONTENT_TYPE_SHORTS, rulesSummary, debugInfo);
        }
    }

    private void traverseNode(AccessibilityNodeInfo node, List<String> texts, List<String> descs, List<String> ids, int depth) {
        if (node == null || depth > 30) return;

        CharSequence text = node.getText();
        if (text != null && text.length() > 0) {
            texts.add(text.toString());
        }

        CharSequence desc = node.getContentDescription();
        if (desc != null && desc.length() > 0) {
            descs.add(desc.toString());
        }

        String viewId = node.getViewIdResourceName();
        if (viewId != null && viewId.length() > 0) {
            ids.add(viewId);
        }

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                traverseNode(child, texts, descs, ids, depth + 1);
                child.recycle();
            }
        }
    }
}
