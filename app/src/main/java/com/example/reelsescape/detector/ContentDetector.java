package com.example.reelsescape.detector;

import android.view.accessibility.AccessibilityNodeInfo;

public interface ContentDetector {
    DetectionResult detect(AccessibilityNodeInfo rootNode, String packageName, int confidenceThreshold);
}
