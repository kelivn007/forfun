/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.activity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
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
import forfun.com.guesssong.ui.view.TitleBar;
import forfun.com.guesssong.ui.view.WaveFormView;
import forfun.com.guesssong.util.FLog;
import forfun.com.guesssong.util.Scalpel;
import forfun.com.guesssong.util.SoundPlayer;
import forfun.com.guesssong.util.WordUtil;

public class MainActivity extends BaseActivity implements ISelectWordListener, MainPresenter
        .IMainPresenter, LoaderManager.LoaderCallbacks<MainPresenter> {

    @Scalpel.InjectView(id = R.id.top_bar_panel)
    private TitleBar mTitleBar;

    @Scalpel.InjectView(id = R.id.btn_record_play, clickable = true)
    private ImageView mBtnRecordPlay;

    @Scalpel.InjectView(id = R.id.img_record_disc)
    private ImageView mImgRecordDisc;
    @Scalpel.InjectView(animId = R.anim.disc_rotate)
    private Animation mDiscAnim;

    @Scalpel.InjectView(id = R.id.img_record_pin)
    private ImageView mImgRecordPin;
    @Scalpel.InjectView(animId = R.anim.record_pin_rotate_in)
    private Animation mRecordPinAnimIn;
    @Scalpel.InjectView(animId = R.anim.record_pin_rotate_out)
    private Animation mRecordPinAnimOut;

    @Scalpel.InjectView(id = R.id.answer_word_panel)
    private ViewGroup mAnswerWordView;

    @Scalpel.InjectView(id = R.id.select_word_view)
    private SelectWordView mSelectWordView;

    @Scalpel.InjectView(id = R.id.delete_answer_btn, clickable = true)
    private ImageButton mDeleteAnswerBtn;
    @Scalpel.InjectView(id = R.id.tip_answer_btn, clickable = true)
    private ImageButton mTipAnswerBtn;

    private AlertDialog mNextLevelDialog;
    private ViewGroup mNextLevelPannel;
    private TextView mNextLevelStageTxt;
    private TextView mNextLevelSongNameTxt;
    private ImageButton mNextLevelBtn;

    @Scalpel.InjectView(id = R.id.view_wave)
    private WaveFormView mWaveFormView;

    private MainPresenter mMainPresenter;

    protected void init() {
        mSelectWordView.setSelectListener(this);

        mDiscAnim.setInterpolator(new LinearInterpolator());

        mRecordPinAnimIn.setFillAfter(true);
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

        mNextLevelPannel = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.next_level_layout, null);
        mNextLevelStageTxt = (TextView) mNextLevelPannel.findViewById(R.id.stage_txt);
        mNextLevelSongNameTxt = (TextView) mNextLevelPannel.findViewById(R.id.song_name);
        mNextLevelBtn = (ImageButton) mNextLevelPannel.findViewById(R.id.next_level_btn);
        mNextLevelBtn.setOnClickListener(this);
        mNextLevelDialog = new AlertDialog.Builder(this).setView(mNextLevelPannel).create();
        mNextLevelDialog.setCancelable(false);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void dispatchClick(int id) {
        switch (id) {
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

        if (null != mImgRecordDisc) {
            mImgRecordDisc.clearAnimation();
            mImgRecordPin.clearAnimation();
        }

        if (null != mMainPresenter) {
            mMainPresenter.pause();
        }

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
        if (mNextLevelDialog.isShowing()) {
            mNextLevelDialog.dismiss();
        }

        LayoutInflater inflater = LayoutInflater.from(this);
        if (null != song) {
            mTitleBar.updateStage(stage + "");
            mAnswerWordView.removeAllViews();
            for (int i = 0; i < song.getName().length(); i++) {
                TextView answerTxt = (TextView) inflater.inflate(R.layout.answer_word_item, null);
                answerTxt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        TextView textView = (TextView) v;
                        String word = textView.getText().toString();
                        if (!TextUtils.isEmpty(word)) {
                            mSelectWordView.resetWordBtn(word);
                            textView.setText("");
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
    public void showPassView(final int stage, final Song song) {
        FLog.d("showPassView stage=" + stage + " song=" + song);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!mNextLevelDialog.isShowing()) {
                    mNextLevelStageTxt.setText(stage + "");
                    mNextLevelSongNameTxt.setText(song.getName());
                    mNextLevelDialog.show();
                }
            }
        });
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
        mTitleBar.updateCoin(num + "");
    }

    @Override
    public void updateWaveForm(byte[] form) {
        mWaveFormView.updateWaveForm(form);
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
