/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost.ams;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import android.os.Handler;

/**
 * Created by huangwei05 on 16/9/14.
 */
public class AMSHookHelper {
    private static final String TAG = "AMSHookHelper";

    public static void hookActivityManagerNative() {
        try {
            Class<?> activityManagerClass = Class.forName("android.app.ActivityManagerNative");
            Field gDefaultField = activityManagerClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            Object gDefault = gDefaultField.get(null);

            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            Object IActivityManager = mInstanceField.get(gDefault);

            Object activityManagerProxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    IActivityManager.getClass().getInterfaces(), new ActivityManagerHandler(IActivityManager));

            mInstanceField.set(gDefault, activityManagerProxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void hookActivityThreadHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Field currentActivityThreadField = activityThreadClass.getDeclaredField("sCurrentActivityThread");
            currentActivityThreadField.setAccessible(true);
            Object currentAcitivityThread = currentActivityThreadField.get(null);

            Field HField = activityThreadClass.getDeclaredField("mH");
            HField.setAccessible(true);
            Handler handler = (Handler) HField.get(currentAcitivityThread);

            Field callbackField = Handler.class.getDeclaredField("mCallback");
            callbackField.setAccessible(true);
            callbackField.set(handler, new ActivityThreadHandlerCallback(handler));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
