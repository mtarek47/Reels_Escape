package com.example.reelsescape.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Handler;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.reelsescape.constants.AppConstants;
import com.example.reelsescape.database.EscapeEventEntity;
import com.example.reelsescape.detector.DetectionEngine;
import com.example.reelsescape.detector.DetectionResult;
import com.example.reelsescape.detector.PackageDetector;
import com.example.reelsescape.model.AppSettings;
import com.example.reelsescape.repository.EscapeRepository;
import com.example.reelsescape.repository.SettingsRepository;
import com.example.reelsescape.util.Logger;
import com.example.reelsescape.util.PackageUtils;

public class ReelsAccessibilityService extends AccessibilityService {

    private SettingsRepository settingsRepository;
    private EscapeRepository escapeRepository;
    private DetectionEngine detectionEngine;

    private ServiceState currentState = ServiceState.IDLE;
    private long lastAnalysisTime = 0;
    private long lastEscapeTime = 0;

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        try {
            if (settingsRepository == null) settingsRepository = new SettingsRepository(this);
            if (escapeRepository == null) escapeRepository = new EscapeRepository(this);
            if (detectionEngine == null) detectionEngine = new DetectionEngine();
            currentState = ServiceState.MONITORING;
            Logger.i("ReelsAccessibilityService", "Service connected and active.");
        } catch (Exception e) {
            Logger.e("ReelsAccessibilityService", "Error during onServiceConnected", e);
            currentState = ServiceState.IDLE;
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        if (event == null) return;

        try {
            long currentTime = System.currentTimeMillis();

            // 1. Cooldown & State Guard
            if (currentState == ServiceState.COOLDOWN || currentState == ServiceState.ESCAPING) {
                return;
            }

            if (currentTime - lastEscapeTime < AppConstants.DEFAULT_COOLDOWN_MS) {
                return;
            }

            // 2. Debouncing
            if (currentTime - lastAnalysisTime < AppConstants.DEFAULT_DEBOUNCE_MS) {
                return;
            }

            CharSequence pkgCharSequence = event.getPackageName();
            if (pkgCharSequence == null) return;
            String packageName = pkgCharSequence.toString();

            if (!PackageDetector.isSupportedPackage(packageName)) {
                return;
            }

            if (settingsRepository == null) {
                settingsRepository = new SettingsRepository(this);
            }
            AppSettings settings = settingsRepository.getSettings();
            if (settings == null) return;

            if (!settings.isMasterProtectionEnabled() || !settings.isPackageEnabled(packageName)) {
                return;
            }

            // 3. Obtain Root Node
            AccessibilityNodeInfo rootNode = getRootInActiveWindow();
            if (rootNode == null) return;

            lastAnalysisTime = currentTime;
            currentState = ServiceState.POSSIBLE_SHORT_FORM;

            try {
                if (detectionEngine == null) {
                    detectionEngine = new DetectionEngine();
                }
                // 4. Run Detection Engine
                DetectionResult result = detectionEngine.analyze(rootNode, packageName, settings);

                if (result != null && result.isDetected()) {
                    currentState = ServiceState.CONFIRMED_SHORT_FORM;
                    executeEscape(packageName, result);
                } else {
                    currentState = ServiceState.MONITORING;
                }
            } catch (Exception e) {
                Logger.e("ReelsAccessibilityService", "Error during screen analysis", e);
                currentState = ServiceState.MONITORING;
            } finally {
                rootNode.recycle();
            }
        } catch (Exception e) {
            Logger.e("ReelsAccessibilityService", "Top-level exception in onAccessibilityEvent", e);
        }
    }

    private void executeEscape(String packageName, DetectionResult result) {
        currentState = ServiceState.ESCAPING;
        Logger.i("ReelsAccessibilityService", "Shorts/Reels detected! Executing GLOBAL_ACTION_BACK for " + packageName);

        // Perform Back Action
        boolean success = performGlobalAction(GLOBAL_ACTION_BACK);

        if (success) {
            lastEscapeTime = System.currentTimeMillis();

            // Record Event in Room Database
            String appName = PackageUtils.getAppNameForPackage(packageName);
            String contentType = result.getContentType();
            EscapeEventEntity entity = new EscapeEventEntity(
                    lastEscapeTime,
                    packageName,
                    appName,
                    contentType,
                    result.getConfidence(),
                    result.getMatchedRule()
            );

            if (escapeRepository == null) {
                escapeRepository = new EscapeRepository(this);
            }
            escapeRepository.recordEscape(entity);
        }

        // Enter Cooldown State
        currentState = ServiceState.COOLDOWN;
        handler.postDelayed(() -> {
            currentState = ServiceState.MONITORING;
            Logger.d("ReelsAccessibilityService", "Cooldown ended. Resuming monitoring.");
        }, AppConstants.DEFAULT_COOLDOWN_MS);
    }

    @Override
    public void onInterrupt() {
        Logger.w("ReelsAccessibilityService", "Service interrupted.");
        currentState = ServiceState.IDLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("ReelsAccessibilityService", "Service destroyed.");
        currentState = ServiceState.IDLE;
    }

    public ServiceState getCurrentState() {
        return currentState;
    }
}
