/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.model;

import java.util.ArrayList;

import android.media.audiofx.Visualizer;
import forfun.com.guesssong.model.bean.Song;
import forfun.com.guesssong.util.Constant;
import forfun.com.guesssong.util.DataUtil;
import forfun.com.guesssong.util.FLog;
import forfun.com.guesssong.util.SoundPlayer;

/**
 * Created by huangwei05 on 16/8/20.
 */
public class SongModel {

    public enum ANSWERSTATE {
        UNCOMPLETE,
        WRONG,
        RIGTH
    }

    private ArrayList<Song> mSongArr = new ArrayList<Song>();
    private int mCurrentStage = 1;
    private Song mCurrentSong;
    private Visualizer mVisualizer;

    public SongModel() {

    }

    public void init(Visualizer.OnDataCaptureListener linstener) {
        for (int i = 0; i < Constant.SONG_INFO.length; i++) {
            mSongArr.add(new Song(Constant.SONG_INFO[i][0], Constant.SONG_INFO[i][1]));
        }

        mCurrentStage = DataUtil.readStage();
        mCurrentSong = getNextSong();

        mVisualizer = new Visualizer(0);
        mVisualizer.setDataCaptureListener(linstener, Visualizer.getMaxCaptureRate(), true, false);
        mVisualizer.setCaptureSize(256);
        mVisualizer.setEnabled(true);
    }

    public void playCurrent() {
        SoundPlayer.playFile(mCurrentSong.getPath());
    }

    public int getCurrentStage() {
        return mCurrentStage;
    }

    public Song getCurrentSong() {
        return mCurrentSong;
    }


    public ANSWERSTATE checkAnswer(String answer) {
        if (mCurrentSong.getName().length() > answer.length()) {
            return ANSWERSTATE.UNCOMPLETE;
        }

        return answer.equalsIgnoreCase(mCurrentSong.getName()) ? ANSWERSTATE.RIGTH : ANSWERSTATE.WRONG;
    }

    public boolean nextSong() {
        mCurrentStage++;
        mCurrentSong = getNextSong();
        if (null != mCurrentSong) {
            return true;
        }

        return false;
    }

    private Song getNextSong() {
        if (mCurrentStage < mSongArr.size() + 1) {
            Song song = mSongArr.get(mCurrentStage - 1);
            FLog.d("current stage : " + mCurrentStage + " " + song.getName());
            return song;
        }

        return null;
    }


}
