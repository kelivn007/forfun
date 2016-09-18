/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost.ams;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.content.ComponentName;
import android.content.Intent;
import forfun.com.ghost.StubActivity;

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
            ComponentName componentName = new ComponentName("forfun.com.ghost", StubActivity.class.getName());
            Intent hookIntent = new Intent();
            hookIntent.setComponent(componentName);

            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Intent) {
                    hookIntent.putExtra("real", ((Intent)args[i]).getComponent());
                    args[i] = hookIntent;
                    break;
                }
            }

            return method.invoke(mBase, args);
        }

        return method.invoke(mBase, args);
    }
}
