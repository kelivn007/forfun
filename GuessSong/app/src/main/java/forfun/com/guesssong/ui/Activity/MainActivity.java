/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.Activity;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
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
import forfun.com.guesssong.model.SelectWord;
import forfun.com.guesssong.model.Song;
import forfun.com.guesssong.ui.View.DialogHelper;
import forfun.com.guesssong.ui.View.SelectWordView;
import forfun.com.guesssong.util.Constant;
import forfun.com.guesssong.util.DataUtil;
import forfun.com.guesssong.util.FLog;
import forfun.com.guesssong.util.SoundPlayer;

public class MainActivity extends Activity implements View.OnClickListener, ISelectWordListener {

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

    private static final int UNCOMPELETE_ANSWER = 0;
    private static final int WRONG_ANSWER = 1;
    private static final int RIGHT_ANSWER = 2;

    private Song mCurrentSong;
    private TextView mCurrentCoinTxt;
    private int mCurrentCoins = 300;
    private int mCurrentStage = 1;

    private ArrayList<Song> mSongArr = new ArrayList<Song>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

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

        mRecordPinAnimIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImgRecordDisc.startAnimation(mDiscAnim);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mDiscAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mImgRecordPin.startAnimation(mRecordPinAnimOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mAnswerWordView = (ViewGroup) findViewById(R.id.answer_word_panel);
        mSelectWordView = (SelectWordView) findViewById(R.id.select_word_view);
        mSelectWordView.setSelectListener(this);

        mCurrentCoinTxt = (TextView) findViewById(R.id.current_coin_txt);
        mCurrentCoinTxt.setText(mCurrentCoins + "");

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

        mCurrentStage = DataUtil.readStage(this);
        mCurrentCoins = DataUtil.readCoin(this);
        initSongData();
        prepareNextSong();

    }

    private void initSongData() {

        for (int i = 0; i < Constant.SONG_INFO.length; i++) {
            mSongArr.add(new Song(Constant.SONG_INFO[i][0], Constant.SONG_INFO[i][1]));
        }

    }

    private boolean prepareNextSong() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mCurrentSong = getNextSong();
        if (null != mCurrentSong) {
            mCurrentStageTxt.setText(mCurrentStage + "");
            mAnswerWordView.removeAllViews();
            for (int i = 0; i < mCurrentSong.getName().length(); i++) {
                TextView answerTxt = (TextView) inflater.inflate(R.layout.answer_word_item, null);
                answerTxt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        SelectWord word = (SelectWord) v.getTag();
                        if (null != word) {
                            word.mSelectWordBtn.setVisibility(View.VISIBLE);
                            ((TextView) v).setText("");
                            v.setTag(null);
                        }
                    }
                });
                mAnswerWordView.addView(answerTxt);
            }

            String[] words = generateWords(mCurrentSong);
            mSelectWordView.updateData(words);

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

    /**
     * 生成所有的待选文字
     *
     * @return
     */
    private String[] generateWords(Song song) {
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
    private char getRandomChar() {
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_record_play:
                SoundPlayer.playFile(this, mCurrentSong.getPath());
                mImgRecordPin.startAnimation(mRecordPinAnimIn);
                break;
            case R.id.delete_answer_btn:
                if (mCurrentCoins - getResources().getInteger(R.integer.pay_delete_answer) < 0) {
                    handleLackCoinsEvent();
                    return;
                }

                DialogHelper.create(this, "确认花掉30个金币").setOnDialogClickListener(
                        new DialogHelper.onDialogClickListener() {

                            @Override
                            public void onClick(boolean confirm) {
                                if (confirm) {
                                    handleDeleteAnswer();
                                } else {
                                    SoundPlayer.playCancel(MainActivity.this);
                                }
                            }
                        }).show();
                break;
            case R.id.tip_answer_btn:
                if (mCurrentCoins - getResources().getInteger(R.integer.pay_tip_answer) < 0) {
                    handleLackCoinsEvent();
                    return;
                }
                DialogHelper.create(this, "确认花掉90个金币").setOnDialogClickListener(
                        new DialogHelper.onDialogClickListener() {

                            @Override
                            public void onClick(boolean confirm) {
                                if (confirm) {
                                    handleTipAnswer();
                                } else {
                                    SoundPlayer.playCancel(MainActivity.this);
                                }
                            }
                        }).show();
                break;
            case R.id.next_level_btn:
                nextLevel();
                break;
        }

    }

    private void handleDeleteAnswer() {
        SoundPlayer.playCoin(this);

        mCurrentCoins -= getResources().getInteger(R.integer.pay_delete_answer);
        mCurrentCoinTxt.setText(mCurrentCoins + "");

        SelectWord word = findDeleteWord();
        word.mSelectWordBtn.setVisibility(View.INVISIBLE);

    }

    private SelectWord findDeleteWord() {
        Random random = new Random(System.currentTimeMillis());

        while (true) {
            SelectWord word = (SelectWord) mSelectWordView.getChildAt(random.nextInt(18)).getTag();
            if (word.mSelectWordBtn.getVisibility() == View.VISIBLE && !mCurrentSong.getName().contains(word
                    .mWord)) {
                return word;
            }
        }
    }

    private void handleTipAnswer() {

        for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
            if (null == mAnswerWordView.getChildAt(i).getTag()) {
                SelectWord word = findTipAnswer(i);
                if (null != word) {
                    SoundPlayer.playCoin(this);

                    mCurrentCoins -= getResources().getInteger(R.integer.pay_tip_answer);
                    mCurrentCoinTxt.setText(mCurrentCoins + "");

                    onWordSelect(word);
                    return;
                }
            }
        }
    }

    private SelectWord findTipAnswer(int index) {
        for (int i = 0; i < 18; i++) {
            SelectWord word = (SelectWord) mSelectWordView.getChildAt(i).getTag();
            if (word.mSelectWordBtn.getVisibility() == View.VISIBLE && word
                    .mWord.equalsIgnoreCase(String.valueOf(mCurrentSong.getName().charAt(index)))) {
                return word;
            }
        }

        return null;
    }

    private void handleLackCoinsEvent() {
        DialogHelper.create(this, "金币不足").show();
    }

    @Override
    protected void onPause() {
        super.onPause();

        mImgRecordDisc.clearAnimation();
        mImgRecordPin.clearAnimation();

        SoundPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SoundPlayer.destroy();

        DataUtil.writeStage(this, mCurrentStage);
        DataUtil.writeCoin(this, mCurrentCoins);
    }

    @Override
    public void onWordSelect(SelectWord word) {
        for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
            TextView answerTxt = (TextView) mAnswerWordView.getChildAt(i);
            if (TextUtils.isEmpty(answerTxt.getText())) {
                answerTxt.setText(word.mWord);
                answerTxt.setTag(word);
                word.mSelectWordBtn.setVisibility(View.INVISIBLE);
                break;
            }
        }

        switch (checkAnswerWord()) {
            case UNCOMPELETE_ANSWER:
                FLog.d("uncomplete_answer");
                for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
                    TextView answerTxt = (TextView) mAnswerWordView.getChildAt(i);
                    answerTxt.setTextColor(Color.WHITE);
                }
                break;
            case WRONG_ANSWER:
                FLog.d("wrong_answer");
                sparkAnswer();
                break;
            case RIGHT_ANSWER:
                FLog.d("right_answer");
                showPassView();
                break;
        }
    }

    public int checkAnswerWord() {
        String answerstr = "";
        for (int i = 0; i < mAnswerWordView.getChildCount(); i++) {
            SelectWord word = (SelectWord) mAnswerWordView.getChildAt(i).getTag();
            if (null == word) {
                return UNCOMPELETE_ANSWER;
            }

            answerstr = answerstr + word.mWord;
        }

        FLog.d("answerstr = " + answerstr);
        return answerstr.equalsIgnoreCase(mCurrentSong.getName()) ? RIGHT_ANSWER : WRONG_ANSWER;
    }

    private int currentSparkTime = 0;
    private static int TIME = 4;

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

    public void showPassView() {
        SoundPlayer.playCoin(this);
        mNextLevelPannel.setVisibility(View.VISIBLE);
        mNextLevelStageTxt.setText(mCurrentStage + "");
        mNextLevelSongNameTxt.setText(mCurrentSong.getName());
    }

    public void nextLevel() {
        mCurrentStage++;
        mImgRecordDisc.clearAnimation();
        mImgRecordPin.clearAnimation();

        SoundPlayer.pause();
        if (prepareNextSong()) {
            SoundPlayer.playEnter(this);
            mNextLevelPannel.setVisibility(View.INVISIBLE);
        } else {
            startActivity(new Intent(this, AllPassActivity.class));
        }
    }
}
