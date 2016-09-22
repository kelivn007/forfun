/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by huangwei05 on 16/7/25.
 */
public class DataUtil {

    private static final String KEY_STAGE = "stage";
    private static final String KEY_COIN = "coin";
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static int readStage() {
        int stage = readInt(KEY_STAGE, 1);

        return stage > Constant.SONG_INFO.length ? 1 : stage;
    }

    public static int readCoin() {
        int coin = readInt(KEY_COIN, 300);
        return coin > 0 ? coin : 0;
    }

    public static int readInt(String key, int defaulValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getInt(key, defaulValue);
    }

    public static void writeStage(int stage) {
        writeInt(KEY_STAGE, stage);
    }

    public static void writeCoin(int coin) {
        writeInt(KEY_COIN, coin);
    }

    public static void writeInt(String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putInt(key, value).commit();
    }
}
