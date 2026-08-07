package com.example.reelsescape.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityManager;

import com.example.reelsescape.service.ReelsAccessibilityService;

import java.util.List;

public class AccessibilityUtils {

    public static boolean isAccessibilityServiceEnabled(Context context) {
        if (context == null) return false;

        Context appContext = context.getApplicationContext();
        String expectedPackageName = appContext.getPackageName();
        String expectedClassName = ReelsAccessibilityService.class.getName();
        String simpleClassName = ReelsAccessibilityService.class.getSimpleName();

        // 1. Direct check via Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        try {
            String settingValue = Settings.Secure.getString(
                    appContext.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);

            if (settingValue != null && !settingValue.isEmpty()) {
                TextUtils.SimpleStringSplitter stringSplitter = new TextUtils.SimpleStringSplitter(':');
                stringSplitter.setString(settingValue);

                while (stringSplitter.hasNext()) {
                    String serviceStr = stringSplitter.next();
                    ComponentName cn = ComponentName.unflattenFromString(serviceStr);
                    if (cn != null && expectedPackageName.equals(cn.getPackageName())
                            && expectedClassName.equals(cn.getClassName())) {
                        return true;
                    }
                    if (serviceStr.contains(expectedPackageName) && serviceStr.contains(simpleClassName)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            Logger.e("AccessibilityUtils", "Error checking ENABLED_ACCESSIBILITY_SERVICES", e);
        }

        // 2. Fallback check via AccessibilityManager
        try {
            AccessibilityManager am = (AccessibilityManager) appContext.getSystemService(Context.ACCESSIBILITY_SERVICE);
            if (am != null) {
                List<AccessibilityServiceInfo> runningServices = am.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK);
                if (runningServices != null) {
                    for (AccessibilityServiceInfo info : runningServices) {
                        if (info.getId() != null) {
                            ComponentName cn = ComponentName.unflattenFromString(info.getId());
                            if (cn != null && expectedPackageName.equals(cn.getPackageName())
                                    && expectedClassName.equals(cn.getClassName())) {
                                return true;
                            }
                            if (info.getId().contains(simpleClassName)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.e("AccessibilityUtils", "Error checking AccessibilityManager", e);
        }

        return false;
    }

    public static void openAccessibilitySettings(Context context) {
        if (context == null) return;

        ComponentName componentName = new ComponentName(context, ReelsAccessibilityService.class);
        String serviceId = componentName.flattenToString();

        // 1. Try Direct AccessibilityDetailsSettingsActivity (Android 11+ / ColorOS / OxygenOS / MIUI / OneUI)
        try {
            Intent directIntent = new Intent();
            directIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessibilityDetailsSettingsActivity"));
            directIntent.putExtra("extra_component_name", serviceId);
            directIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(directIntent);
            return;
        } catch (Exception ignored) {
        }

        // 2. Try fragment args Intent
        try {
            Intent fragmentIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            fragmentIntent.putExtra(":settings:fragment_args_key", serviceId);
            android.os.Bundle bundle = new android.os.Bundle();
            bundle.putString(":settings:fragment_args_key", serviceId);
            fragmentIntent.putExtra(":settings:show_fragment_args", bundle);
            fragmentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(fragmentIntent);
            return;
        } catch (Exception ignored) {
        }

        // 3. Fallback to standard Accessibility Settings list
        try {
            Intent genericIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            genericIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(genericIntent);
        } catch (Exception e) {
            Logger.e("AccessibilityUtils", "Could not open accessibility settings", e);
        }
    }
}
