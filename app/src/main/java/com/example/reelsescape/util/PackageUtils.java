package com.example.reelsescape.util;

import android.content.Context;
import android.content.pm.PackageManager;

import com.example.reelsescape.constants.AppConstants;

public class PackageUtils {

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static String getAppNameForPackage(String packageName) {
        if (AppConstants.PACKAGE_YOUTUBE.equalsIgnoreCase(packageName)) {
            return AppConstants.APP_NAME_YOUTUBE;
        } else if (AppConstants.PACKAGE_INSTAGRAM.equalsIgnoreCase(packageName)) {
            return AppConstants.APP_NAME_INSTAGRAM;
        } else if (AppConstants.PACKAGE_FACEBOOK.equalsIgnoreCase(packageName)) {
            return AppConstants.APP_NAME_FACEBOOK;
        }
        return packageName;
    }

    public static String getContentTypeForPackage(String packageName) {
        if (AppConstants.PACKAGE_YOUTUBE.equalsIgnoreCase(packageName)) {
            return AppConstants.CONTENT_TYPE_SHORTS;
        } else {
            return AppConstants.CONTENT_TYPE_REELS;
        }
    }
}
