/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.presenter.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.media.audiofx.Visualizer;
import forfun.com.guesssong.model.SongModel;
import forfun.com.guesssong.model.bean.Song;
import forfun.com.guesssong.presenter.BasePresenter;
import forfun.com.guesssong.util.DataUtil;
import forfun.com.guesssong.util.FLog;
import forfun.com.guesssong.util.SoundPlayer;


/**
 * Created by huangwei05 on 16/8/14.
 */
public class MainPresenter extends BasePresenter implements Visualizer.OnDataCaptureListener {
    public interface IMainPresenter {
        public void showPlaySong();
        public void showAnswerView(int stage, Song song);
        public void sparkAnswer();
        public void showPassView(int stage, Song song);
        public void showAllPassView();
        public void showDeleteAnswerConfirm();
        public void handleDeleteAnswer(Song song);
        public void showTipAnswer();
        public void handleTipAnswer(Song song);
        public void showLackCoins();
        public void updateCoins(int num);
        public void updateWaveForm(byte[] form);
    }

    public static final int DELETE_ANSWER_COIN= 30;
    public static final int TIP_ANSWER_COIN = 90;

    private IMainPresenter mListener;
    private SongModel mSongModel;
    private int mCurrentCoins = 300;
    private ExecutorService mExecutorSevice;

    public MainPresenter() {
        mSongModel = new SongModel();
        mSongModel.init(this);

        mCurrentCoins = DataUtil.readCoin();
        mExecutorSevice = Executors.newCachedThreadPool();
    }

    public void regist(IMainPresenter listener) {
        mListener = listener;

        mListener.showAnswerView(mSongModel.getCurrentStage(), mSongModel.getCurrentSong());
        mListener.updateCoins(mCurrentCoins);
    }

    public void clickPlaySong() {
        mSongModel.playCurrent();
        mListener.showPlaySong();
    }

    public void checkAnswerWord(final String answer) {
        mExecutorSevice.submit(new Runnable() {
            @Override
            public void run() {
                FLog.d("answerstr = " + answer);

                switch (mSongModel.checkAnswer(answer)) {
                    case UNCOMPLETE :
                        break;
                    case WRONG:
                        mListener.sparkAnswer();
                        break;
                    case RIGTH:
                        SoundPlayer.playCoin();
                        mListener.showPassView(mSongModel.getCurrentStage(), mSongModel.getCurrentSong());
                        break;
                }
            }
        });
    }

    public void clickNextLevel() {
        SoundPlayer.pause();
        if (mSongModel.nextSong()) {
            SoundPlayer.playEnter();
            mCurrentCoins += 30;
            mListener.updateCoins(mCurrentCoins);
            mListener.showAnswerView(mSongModel.getCurrentStage(), mSongModel.getCurrentSong());
        } else {
            mListener.showAllPassView();
        }
    }

    public void clickDeleteAnswer() {
        if (mCurrentCoins - DELETE_ANSWER_COIN < 0) {
            mListener.showLackCoins();
        } else {
            mListener.showDeleteAnswerConfirm();
        }
    }

    public void confirmDelete(boolean confirm) {
        if (confirm) {
            SoundPlayer.playCoin();
            mCurrentCoins -= DELETE_ANSWER_COIN;
            mListener.updateCoins(mCurrentCoins);
            mListener.handleDeleteAnswer(mSongModel.getCurrentSong());
        } else {
            SoundPlayer.playCancel();
        }
    }

    public void clickTipAnswer() {
        if (mCurrentCoins - TIP_ANSWER_COIN < 0) {
            mListener.showLackCoins();
        } else {
            mListener.showTipAnswer();
        }
    }

    public void confirmTipAnswer(boolean confirm) {
        if (confirm) {
            SoundPlayer.playCoin();
            mCurrentCoins -= TIP_ANSWER_COIN;
            mListener.updateCoins(mCurrentCoins);
            mListener.handleTipAnswer(mSongModel.getCurrentSong());
        } else {
            SoundPlayer.playCancel();
        }

    }

    public void pause() {
        SoundPlayer.pause();
    }

    public void save() {
        DataUtil.writeStage(mSongModel.getCurrentStage());
        DataUtil.writeCoin(mCurrentCoins);
    }

    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
        mListener.updateWaveForm(waveform);
    }

    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

    }

}
