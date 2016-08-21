/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.activity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import forfun.com.guesssong.R;
import forfun.com.guesssong.model.ISelectWordListener;
import forfun.com.guesssong.model.bean.SelectWord;
import forfun.com.guesssong.model.bean.Song;
import forfun.com.guesssong.presenter.PresenterLoader;
import forfun.com.guesssong.presenter.impl.MainPresenter;
import forfun.com.guesssong.ui.view.DialogHelper;
import forfun.com.guesssong.ui.view.MyAnimationListener;
import forfun.com.guesssong.ui.view.SelectWordView;
import forfun.com.guesssong.util.FLog;
import forfun.com.guesssong.util.SoundPlayer;
import forfun.com.guesssong.util.WordUtil;

public class MainActivity extends Activity implements View.OnClickListener, ISelectWordListener, MainPresenter
        .IMainPresenter, LoaderManager.LoaderCallbacks<MainPresenter> {

    private ImageView mBtnRecordPlay;
    private ImageView mImgRecordDisc;
    private Animation mDiscAnim;

    private ImageView mImgRecordPin;
    private Animation mRecordPinAnimIn;
    private Animation mRecordPinAnimOut;

    private ViewGroup mAnswerWordView;
    private SelectWordView mSelectWordView;

    private ImageButton mDeleteAnswerBtn;
    private ImageButton mTipAnswerBtn;

    private ViewGroup mNextLevelPannel;
    private TextView mNextLevelStageTxt;
    private TextView mNextLevelSongNameTxt;
    private ImageButton mNextLevelBtn;
    private TextView mCurrentStageTxt;

    private TextView mCurrentCoinTxt;

    private MainPresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRecordPlay = (ImageView) findViewById(R.id.btn_record_play);
        mBtnRecordPlay.setOnClickListener(this);
        mImgRecordDisc = (ImageView) findViewById(R.id.img_record_disc);
        mDiscAnim = AnimationUtils.loadAnimation(this, R.anim.disc_rotate);
        mDiscAnim.setInterpolator(new LinearInterpolator());

        mImgRecordPin = (ImageView) findViewById(R.id.img_record_pin);
        mRecordPinAnimIn = AnimationUtils.loadAnimation(this, R.anim.record_pin_rotate_in);
        mRecordPinAnimIn.setFillAfter(true);
        mRecordPinAnimOut = AnimationUtils.loadAnimation(this, R.anim.record_pin_rotate_out);
        mRecordPinAnimOut.setFillAfter(true);

        mRecordPinAnimIn.setAnimationListener(new MyAnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                mImgRecordDisc.startAnimation(mDiscAnim);
            }

        });

        mDiscAnim.setAnimationListener(new MyAnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                mImgRecordPin.startAnimation(mRecordPinAnimOut);
            }

        });

        mAnswerWordView = (ViewGroup) findViewById(R.id.answer_word_panel);
        mSelectWordView = (SelectWordView) findViewById(R.id.select_word_view);
        mSelectWordView.setSelectListener(this);

        mCurrentCoinTxt = (TextView) findViewById(R.id.current_coin_txt);

        mDeleteAnswerBtn = (ImageButton) findViewById(R.id.delete_answer_btn);
        mDeleteAnswerBtn.setOnClickListener(this);
        mTipAnswerBtn = (ImageButton) findViewById(R.id.tip_answer_btn);
        mTipAnswerBtn.setOnClickListener(this);

        mNextLevelPannel = (ViewGroup) findViewById(R.id.next_level_pannel);
        mNextLevelStageTxt = (TextView) findViewById(R.id.stage_txt);
        mNextLevelSongNameTxt = (TextView) findViewById(R.id.song_name);
        mNextLevelBtn = (ImageButton) findViewById(R.id.next_level_btn);
        mNextLevelBtn.setOnClickListener(this);

        mCurrentStageTxt = (TextView) findViewById(R.id.current_stage_txt);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_record_play:
                mMainPresenter.clickPlaySong();
                break;
            case R.id.delete_answer_btn:
                mMainPresenter.clickDeleteAnswer();
                break;
            case R.id.tip_answer_btn:
                mMainPresenter.clickTipAnswer();
                break;
            case R.id.next_level_btn:
                mMainPresenter.clickNextLevel();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mImgRecordDisc.clearAnimation();
        mImgRecordPin.clearAnimation();
        mMainPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.save();
    }

    @Override
    public void onWordSelect(SelectWord word) {
        String answerstr = "";
        for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
            TextView answerTxt = (TextView) mAnswerWordView.getChildAt(i);

            if (TextUtils.isEmpty(answerTxt.getText())) {
                answerTxt.setText(word.mWord);
                answerTxt.setTag(word);
                FLog.i(word.mSelectWordBtn + "");
                word.mSelectWordBtn.setVisibility(View.INVISIBLE);
                answerstr = answerstr + answerTxt.getText();
                break;
            } else {
                answerstr = answerstr + answerTxt.getText();
            }
        }

        FLog.i("onWordSelect : " + answerstr);
        mMainPresenter.checkAnswerWord(answerstr);
    }

    @Override
    public void showPlaySong() {
        mImgRecordPin.startAnimation(mRecordPinAnimIn);
    }

    @Override
    public void showAnswerView(int stage, Song song) {
        mImgRecordDisc.clearAnimation();
        mImgRecordPin.clearAnimation();
        mNextLevelPannel.setVisibility(View.INVISIBLE);

        LayoutInflater inflater = LayoutInflater.from(this);
        if (null != song) {
            mCurrentStageTxt.setText(stage + "");
            mAnswerWordView.removeAllViews();
            for (int i = 0; i < song.getName().length(); i++) {
                TextView answerTxt = (TextView) inflater.inflate(R.layout.answer_word_item, null);
                answerTxt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SelectWord word = (SelectWord) v.getTag();
                        if (null != word) {
                            FLog.i(word.mSelectWordBtn + "");
                            word.mSelectWordBtn.setVisibility(View.VISIBLE);
                            v.setTag(null);
                            ((TextView) v).setText("");
                        }
                    }
                });
                mAnswerWordView.addView(answerTxt);
            }

            String[] words = WordUtil.generateWords(song);
            mSelectWordView.updateData(words);
        }
    }

    private int currentSparkTime = 0;
    private static int TIME = 4;

    @Override
    public void sparkAnswer() {
        final Timer timer = new Timer();
        currentSparkTime = 0;
        timer.schedule(new TimerTask() {

            private boolean mChange = false;

            @Override
            public void run() {
                FLog.d("run spark");
                if (currentSparkTime < TIME) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
                                TextView answerTxt = (TextView) mAnswerWordView.getChildAt(i);
                                answerTxt.setTextColor(mChange ? Color.RED : Color.WHITE);

                            }

                            mChange = !mChange;
                        }
                    });
                    currentSparkTime++;
                } else {
                    timer.cancel();
                }
            }
        }, 300, 150);
    }

    @Override
    public void showPassView(int stage, Song song) {
        mNextLevelPannel.setVisibility(View.VISIBLE);
        mNextLevelStageTxt.setText(stage + "");
        mNextLevelSongNameTxt.setText(song.getName());
    }

    @Override
    public void showAllPassView() {
        startActivity(new Intent(this, AllPassActivity.class));
    }

    @Override
    public void showDeleteAnswerConfirm() {
        DialogHelper.create(this, "确认花掉30个金币").setOnDialogClickListener(
                new DialogHelper.onDialogClickListener() {

                    @Override
                    public void onClick(boolean confirm) {
                       mMainPresenter.confirmDelete(confirm);
                    }
                }).show();
    }

    @Override
    public void handleDeleteAnswer(Song song) {
        SelectWord word = findDeleteWord(song);
        FLog.i("handleDeleteAnswer : " + word);
        word.mSelectWordBtn.setVisibility(View.INVISIBLE);
    }

    private SelectWord findDeleteWord(Song song) {
        Random random = new Random(System.currentTimeMillis());

        while (true) {
            SelectWord word = (SelectWord) mSelectWordView.getChildAt(random.nextInt(18)).getTag();
            if (word.mSelectWordBtn.getVisibility() == View.VISIBLE && !song.getName().contains(word
                    .mWord)) {
                return word;
            }
        }
    }

    @Override
    public void showTipAnswer() {
        DialogHelper.create(this, "确认花掉90个金币").setOnDialogClickListener(
                new DialogHelper.onDialogClickListener() {

                    @Override
                    public void onClick(boolean confirm) {
                        mMainPresenter.confirmTipAnswer(confirm);
                    }
                }).show();
    }

    @Override
    public void handleTipAnswer(Song song) {
        for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
            if (null == mAnswerWordView.getChildAt(i).getTag()) {
                SelectWord word = findTipAnswer(song, i);
                if (null != word) {
                    SoundPlayer.playCoin();
                    onWordSelect(word);
                    return;
                }
            }
        }
    }

    @Override
    public void showLackCoins() {
        DialogHelper.create(this, "金币不足").show();
    }

    @Override
    public void updateCoins(int num) {
        mCurrentCoinTxt.setText(num + "");
    }

    private SelectWord findTipAnswer(Song song, int index) {
        for (int i = 0; i < 18; i++) {
            SelectWord word = (SelectWord) mSelectWordView.getChildAt(i).getTag();
            if (word.mSelectWordBtn.getVisibility() == View.VISIBLE && word
                    .mWord.equalsIgnoreCase(String.valueOf(song.getName().charAt(index)))) {
                return word;
            }
        }

        return null;
    }

    @Override
    public Loader<MainPresenter> onCreateLoader(int id, Bundle args) {
        FLog.i("onCreateLoader");
        return new PresenterLoader<MainPresenter>(this, MainPresenter.class);
    }

    @Override
    public void onLoadFinished(Loader<MainPresenter> loader, MainPresenter data) {
        FLog.i("onLoadFinished");
        mMainPresenter = data;
        mMainPresenter.regist(this);
    }

    @Override
    public void onLoaderReset(Loader<MainPresenter> loader) {
        mMainPresenter = null;
    }
}
