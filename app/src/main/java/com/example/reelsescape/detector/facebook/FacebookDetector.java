package com.example.reelsescape.detector.facebook;

import android.view.accessibility.AccessibilityNodeInfo;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.detector.ContentDetector;
import com.example.reelsescape.detector.DetectionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * FacebookDetector — Vertical video page detection only.
 *
 * Logic: Facebook e vertical video scrolling page e dhuklei back debo.
 * Newsfeed e kono check nai — just vertical video markers khoji.
 *
 * Vertical video page markers (confirmed from real UI dumps):
 *   fb_reelstab.xml  : "Reel. Swipe up to see more."
 *   fb_fullscreen.xml: "Reel details", "Tap to show video controls"
 *
 * These markers do NOT exist on normal Newsfeed (confirmed from fb_newsfeed.xml).
 */
public class FacebookDetector implements ContentDetector {

    // Exact content-desc strings found ONLY on Facebook Reels pages.
    // Verified against real UI dumps on CPH2001 (Android 11, Facebook 464.x).
    // NOTE: "tap to show video controls" is NOT included — it appears on ALL videos (Newsfeed, profile, etc.)
    private static final String[] REELS_PAGE_MARKERS = {
        "reel. swipe up to see more.",   // Reels tab feed — EXCLUSIVE to Reels tab vertical feed
        "reel details",                   // Fullscreen reel viewer — says "Reel", NOT generic "Video"
        "navigate to your reels profile", // Reels profile nav — EXCLUSIVE to Reels viewer
        "fbshortscomposerattachmentcomponentspec_sticker", // Reels composer sticker
    };

    @Override
    public DetectionResult detect(AccessibilityNodeInfo rootNode, String packageName, int confidenceThreshold) {
        if (rootNode == null || !AppConstants.PACKAGE_FACEBOOK.equals(packageName)) {
            return DetectionResult.notDetected(packageName, "Root null or package mismatch");
        }

        List<String> contentDescs = new ArrayList<>();
        collectContentDescs(rootNode, contentDescs, 0);

        // Check every content-desc against our Reels-exclusive markers
        for (String desc : contentDescs) {
            String d = desc.toLowerCase().trim();
            for (String marker : REELS_PAGE_MARKERS) {
                if (d.equals(marker) || d.startsWith("fbshortscomposer")) {
                    return new DetectionResult(
                            true, 90, packageName,
                            AppConstants.CONTENT_TYPE_REELS,
                            "FB_REELS_PAGE",
                            "Facebook Reels page detected — back. marker=[" + desc + "]"
                    );
                }
            }
        }

        return DetectionResult.notDetected(packageName,
                "No Reels marker found — staying on current page. descs=" + contentDescs.size());
    }

    private void collectContentDescs(AccessibilityNodeInfo node, List<String> descs, int depth) {
        if (node == null || depth > 35) return;

        CharSequence desc = node.getContentDescription();
        if (desc != null && desc.length() > 0) {
            descs.add(desc.toString());
        }

        int count = node.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                collectContentDescs(child, descs, depth + 1);
                child.recycle();
            }
        }
    }
}
