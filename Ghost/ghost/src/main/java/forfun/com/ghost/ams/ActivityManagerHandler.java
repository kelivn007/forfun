/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost.ams;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import forfun.com.ghost.ui.TrendTradeStub;

/**
 * Created by huangwei05 on 16/9/14.
 */
public class ActivityManagerHandler implements InvocationHandler {
    private static final String TAG = "ActivityManagerHandler";

    private Object mBase;

    public ActivityManagerHandler(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        if ("startActivity".equalsIgnoreCase(method.getName())) {
            ComponentName componentName = new ComponentName("forfun.com.ghost", TrendTradeStub.class.getName());
            Intent hookIntent = new Intent();
            hookIntent.setComponent(componentName);

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    Intent intent = (Intent)args[i];
                    ComponentName rawComponentName = intent.getComponent();
                    if (null != rawComponentName) {
                        Log.i(TAG, "replace " + rawComponentName.getClassName() + " to subActivity");
                        hookIntent.putExtra("pkg", rawComponentName.getPackageName());
                        hookIntent.putExtra("cls", rawComponentName.getClassName());
                        args[i] = hookIntent;
                    }
                    break;
                }
            }

            return method.invoke(mBase, args);
        }

        return method.invoke(mBase, args);
    }
}
