package com.jvho.warehouse.utils;

import android.content.Context;

import java.util.Properties;

public class PropertiesUtil {

    public static String getProperty(Context context, String key) {
        String value = null;
        Properties properties = new Properties();
        try {
            properties.load(context.getAssets().open("local.properties"));
            value = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
