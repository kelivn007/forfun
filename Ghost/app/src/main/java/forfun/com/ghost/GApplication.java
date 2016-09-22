/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost;

import java.io.File;
import java.util.Set;

import android.app.Application;
import android.util.Log;
import forfun.com.ghost.ams.AMSHookHelper;
import forfun.com.ghost.classloader.BaseDexClassLoaderHookHelper;
import forfun.com.ghost.util.ApkConfig;
import forfun.com.ghost.util.Utils;

/**
 * Created by huangwei05 on 16/9/22.
 */
public class GApplication extends Application {
    private static final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();

        ApkConfig.init();

        Set<String> allApk = ApkConfig.getAllApk();
        for (String apkName : allApk) {
            installApk(apkName + ".apk");
        }

        AMSHookHelper.hookActivityManagerNative();
        AMSHookHelper.hookActivityThreadHandler();
    }

    private void installApk(String apkFileName) {
        Log.i(TAG, "installApk " + apkFileName);
        File testApk = new File(getFilesDir().getAbsolutePath() + "/" + apkFileName);

        if (!testApk.exists()) {
            Utils.extractAssets(this, apkFileName);
        }


        File apkFile = new File(getFilesDir().getAbsolutePath() + "/" + apkFileName);
        File odexFile = new File(getFilesDir().getAbsolutePath() + "/" + apkFileName + ".odex");
        BaseDexClassLoaderHookHelper.patchClassLoader(getClassLoader(), apkFile, odexFile);
    }
}
