package com.example.reelsescape.detector;

import android.view.accessibility.AccessibilityNodeInfo;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.detector.facebook.FacebookDetector;
import com.example.reelsescape.detector.instagram.InstagramDetector;
import com.example.reelsescape.detector.youtube.YouTubeDetector;
import com.example.reelsescape.model.AppSettings;
import com.example.reelsescape.util.Logger;

import java.util.HashMap;
import java.util.Map;

public class DetectionEngine {

    private final Map<String, ContentDetector> detectors;

    public DetectionEngine() {
        detectors = new HashMap<>();
        detectors.put(AppConstants.PACKAGE_YOUTUBE, new YouTubeDetector());
        detectors.put(AppConstants.PACKAGE_INSTAGRAM, new InstagramDetector());
        detectors.put(AppConstants.PACKAGE_FACEBOOK, new FacebookDetector());
    }

    public DetectionResult analyze(AccessibilityNodeInfo rootNode, String packageName, AppSettings settings) {
        if (rootNode == null || packageName == null) {
            return DetectionResult.notDetected(packageName, "Null root or package");
        }

        if (!PackageDetector.isSupportedPackage(packageName)) {
            return DetectionResult.notDetected(packageName, "Unsupported package: " + packageName);
        }

        if (!settings.isPackageEnabled(packageName)) {
            return DetectionResult.notDetected(packageName, "Package disabled in settings: " + packageName);
        }

        ContentDetector detector = detectors.get(packageName);
        if (detector == null) {
            return DetectionResult.notDetected(packageName, "No detector registered");
        }

        int threshold = settings.getConfidenceThreshold();
        DetectionResult result = detector.detect(rootNode, packageName, threshold);

        // Always log Facebook and Instagram detection for diagnostics
        if (AppConstants.PACKAGE_FACEBOOK.equals(packageName)) {
            Logger.d("DetectionEngine", "[FB] " + result.getDebugSummary());
        } else if (AppConstants.PACKAGE_INSTAGRAM.equals(packageName)) {
            Logger.d("DetectionEngine", "[IG] " + result.getDebugSummary());
        } else if (settings.isDebugMode()) {
            Logger.d("DetectionEngine", result.getDebugSummary());
        }

        return result;
    }
}
