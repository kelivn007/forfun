/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.ghost.ams;

import java.lang.reflect.Field;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by huangwei05 on 16/9/14.
 */
public class ActivityThreadHandlerCallback implements Handler.Callback {
    private static final String TAG = "ThreadHandlerCallback";

    public Handler mBase;

    public ActivityThreadHandlerCallback(Handler handler) {
        mBase = handler;
    }

    @Override
    public boolean handleMessage(Message message) {
        if (100 == message.what && null != message.obj) {
            Object object = message.obj;
            try {
                Field intentField = object.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent intent = (Intent) intentField.get(object);
                ComponentName componentName = intent.getParcelableExtra("real");
                intent.setComponent(componentName);
                Log.i(TAG, intent + "");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mBase.handleMessage(message);
        return true;
    }
}
