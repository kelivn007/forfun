/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import forfun.com.guesssong.util.DataUtil;
import forfun.com.guesssong.util.SoundPlayer;

/**
 * Created by huangwei05 on 16/7/21.
 */
public class GuessSongApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;

        SoundPlayer.init(mContext);
        DataUtil.init(mContext);


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

    }

    public static Context getContext() {
        return mContext;
    }
}
