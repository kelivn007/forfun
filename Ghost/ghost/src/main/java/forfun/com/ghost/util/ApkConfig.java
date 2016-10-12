/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost.util;

import java.util.HashMap;
import java.util.Set;

import android.content.ComponentName;
/**
 * Created by huangwei05 on 16/9/22.
 */
public class ApkConfig {
    private static HashMap<String, ComponentName> apkMap = new HashMap<String, ComponentName>();

    public static void init() {

        ComponentName testComponentName = new ComponentName("forfun.com.testapp", "forfun.com.testapp"
                + ".MainActivity");
        apkMap.put("Test", testComponentName);

        ComponentName trendComponentName = new ComponentName("forfun.com.trendtrade", "forfun.com.trendtrade"
                + ".ui.MainActivity");
        apkMap.put("TrendTrade", trendComponentName);
    }

    public static Set<String> getAllApk() {
        return apkMap.keySet();
    }

    public static ComponentName getApkEntryComponent(String apkName) {
        return apkMap.get(apkName);
    }
}
