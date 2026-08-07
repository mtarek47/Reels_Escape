package com.example.reelsescape.detector.instagram;

import android.view.accessibility.AccessibilityNodeInfo;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.detector.ContentDetector;
import com.example.reelsescape.detector.DetectionResult;

import java.util.ArrayList;
import java.util.List;

/**
 * InstagramDetector — verified using real UI hierarchy dumps from CPH2001 (Android 11)
 *
 * visibility checked traverse ensures off-screen tabs/views (like inactive Home Feed fragment)
 * do not falsely prevent Reels detection.
 */
public class InstagramDetector implements ContentDetector {

    @Override
    public DetectionResult detect(AccessibilityNodeInfo rootNode, String packageName, int confidenceThreshold) {
        if (rootNode == null || !AppConstants.PACKAGE_INSTAGRAM.equals(packageName)) {
            return DetectionResult.notDetected(packageName, "Root null or package mismatch");
        }

        List<String> contentDescs = new ArrayList<>();
        List<String> visibleTexts = new ArrayList<>();
        List<String> viewIds = new ArrayList<>();

        traverseNode(rootNode, visibleTexts, contentDescs, viewIds, 0);

        // ===== GATE 1: NEWSFEED OR EXTERNAL SCREEN DETECTION (ONLY IF VISIBLE) =====
        for (String desc : contentDescs) {
            String d = desc.toLowerCase().trim();
            if (d.equals("reels tray container") || d.equals("instagram home feed")) {
                return DetectionResult.notDetected(packageName,
                        "IG: Newsfeed detected via visible content-desc (" + desc + ") -> No back.");
            }
        }

        for (String viewId : viewIds) {
            if (viewId.contains("title_logo_chevron_container")
                    || viewId.contains("row_feed_profile_header")) {
                return DetectionResult.notDetected(packageName,
                        "IG: Newsfeed post header detected via visible resource-id (" + viewId + ") -> No back.");
            }
        }

        // ===== GATE 2: REELS SCREEN DETECTION (YES BACK) =====
        boolean hasDoubleTapReelMarker = false;
        boolean hasRepostMarker = false;
        boolean hasCreateReelMarker = false;
        boolean hasClipsActionBar = false;

        for (String desc : contentDescs) {
            String d = desc.toLowerCase().trim();
            if (d.startsWith("reel by ") && d.contains("double tap to play")) {
                hasDoubleTapReelMarker = true;
            }
            if (d.equals("repost") || d.contains("reposted")) {
                hasRepostMarker = true;
            }
            if (d.equals("create a reel")) {
                hasCreateReelMarker = true;
            }
        }

        for (String text : visibleTexts) {
            String t = text.toLowerCase().trim();
            if (t.equals("repost") || t.contains("reposted")) {
                hasRepostMarker = true;
            }
        }

        for (String viewId : viewIds) {
            if (viewId.contains("clips_viewer_action_bar")
                    || viewId.contains("clips_viewer_video_layout")
                    || viewId.contains("clips_caption_component")) {
                hasClipsActionBar = true;
            }
        }

        // We trigger back if we have the active fullscreen double-tap video player
        // AND one of the strong reels-only structural markers
        if (hasDoubleTapReelMarker && (hasRepostMarker || hasCreateReelMarker || hasClipsActionBar)) {
            String matchReason = "hasDoubleTap=" + hasDoubleTapReelMarker
                    + " repost=" + hasRepostMarker
                    + " createReel=" + hasCreateReelMarker
                    + " clipsBar=" + hasClipsActionBar;

            return new DetectionResult(
                    true, 95, packageName,
                    AppConstants.CONTENT_TYPE_REELS,
                    "IG_REELS_EXCLUSIVES",
                    "Instagram Reels Vertical Feed/Viewer detected (" + matchReason + ") -> Triggering back."
            );
        }

        String debugInfo = "IG: doubleTap=" + hasDoubleTapReelMarker
                + " repost=" + hasRepostMarker
                + " create=" + hasCreateReelMarker
                + " clipsBar=" + hasClipsActionBar;
        return DetectionResult.notDetected(packageName, "Not active fullscreen reels tab/viewer. Info: " + debugInfo);
    }

    private void traverseNode(AccessibilityNodeInfo node, List<String> texts, List<String> descs, List<String> ids, int depth) {
        if (node == null || depth > 35) return;

        // CRITICAL: Skip nodes that are NOT visible to the user
        // This avoids picking up off-screen views from background fragments (like Newsfeed)
        if (!node.isVisibleToUser()) {
            return;
        }

        CharSequence text = node.getText();
        if (text != null && text.length() > 0) texts.add(text.toString());

        CharSequence desc = node.getContentDescription();
        if (desc != null && desc.length() > 0) descs.add(desc.toString());

        String viewId = node.getViewIdResourceName();
        if (viewId != null && viewId.length() > 0) ids.add(viewId);

        int count = node.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (child != null) {
                traverseNode(child, texts, descs, ids, depth + 1);
                child.recycle();
            }
        }
    }
}
