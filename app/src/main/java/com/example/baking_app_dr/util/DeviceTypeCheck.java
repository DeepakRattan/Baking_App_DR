package com.example.baking_app_dr.util;

import android.content.Context;
import android.content.res.Configuration;

// Created by Deepak Rattan on 23-Jun-19.
public class DeviceTypeCheck {
    //Will return true if the device is operating on a large screen
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
