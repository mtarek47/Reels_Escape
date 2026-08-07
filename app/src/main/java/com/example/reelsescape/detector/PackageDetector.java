package com.example.reelsescape.detector;

import com.example.reelsescape.constants.AppConstants;

public class PackageDetector {

    public static boolean isSupportedPackage(String packageName) {
        if (packageName == null) return false;
        return AppConstants.PACKAGE_YOUTUBE.equals(packageName) ||
                AppConstants.PACKAGE_INSTAGRAM.equals(packageName) ||
                AppConstants.PACKAGE_FACEBOOK.equals(packageName);
    }
}
