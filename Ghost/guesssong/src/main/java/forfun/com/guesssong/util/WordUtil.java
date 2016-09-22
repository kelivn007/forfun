/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import forfun.com.guesssong.model.bean.Song;

/**
 * Created by huangwei05 on 16/8/20.
 */
public class WordUtil {
    /**
     * 生成所有的待选文字
     *
     * @return
     */
    public static String[] generateWords(Song song) {
        int totalWords = 18;
        Random random = new Random(System.currentTimeMillis());

        String[] words = new String[totalWords];

        // 存入歌名
        for (int i = 0; i < song.getName().length(); i++) {
            words[i] = String.valueOf(song.getName().charAt(i));
        }

        // 获取随机文字并存入数组
        for (int i = song.getName().length();
             i < totalWords; i++) {
            words[i] = getRandomChar() + "";
        }

        // 打乱文字顺序：首先从所有元素中随机选取一个与第一个元素进行交换，
        // 然后在第二个之后选择一个元素与第二个交换，知道最后一个元素。
        // 这样能够确保每个元素在每个位置的概率都是1/n。
        for (int i = totalWords - 1; i >= 0; i--) {
            int index = random.nextInt(i + 1);

            String buf = words[index];
            words[index] = words[i];
            words[i] = buf;
        }

        return words;
    }

    /**
     * 生成随机汉字
     *
     * @return
     */
    public static char getRandomChar() {
        String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return str.charAt(0);
    }
}
