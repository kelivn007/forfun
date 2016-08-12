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

    public static int readStage(Context context) {
        int stage = readInt(context, KEY_STAGE, 1);

        return stage > Constant.SONG_INFO.length ? 1 : stage;
    }

    public static int readCoin(Context context) {
        int coin = readInt(context, KEY_COIN, 300);
        return coin > 0 ? coin : 0;
    }

    public static int readInt(Context context, String key, int defaulValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, defaulValue);
    }

    public static void writeStage(Context context, int stage) {
        writeInt(context, KEY_STAGE, stage);
    }

    public static void writeCoin(Context context, int coin) {
        writeInt(context, KEY_COIN, coin);
    }

    public static void writeInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value).commit();
    }
}
