/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

/**
 * Created by huangwei05 on 16/7/25.
 */
public class SoundPlayer {

    private static MediaPlayer mMdeiaPlayer;
    private static final String ENTER_SOUND_PATH = "enter.mp3";
    private static final String CANCEL_SOUND_PATH = "cancel.mp3";
    private static final String COIN_SOUND_PATH = "coin.mp3";

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void playEnter() {
        playFile(ENTER_SOUND_PATH);
    }

    public static void playCancel() {
        playFile(CANCEL_SOUND_PATH);
    }

    public static void playCoin() {
        playFile(COIN_SOUND_PATH);
    }

    public static void playFile(String filePath) {
        if (null == mMdeiaPlayer) {
            mMdeiaPlayer = new MediaPlayer();
        }

        mMdeiaPlayer.reset();

        try {
            AssetFileDescriptor fd = mContext.getAssets().openFd(filePath);
            mMdeiaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mMdeiaPlayer.prepare();
            mMdeiaPlayer.start();
            fd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void pause() {
        if (null != mMdeiaPlayer) {
            mMdeiaPlayer.pause();
        }
    }

    public static void destroy() {
        if (null != mMdeiaPlayer) {
            mMdeiaPlayer.release();
            mMdeiaPlayer = null;
        }
    }
}
