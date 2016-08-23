/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import forfun.com.guesssong.R;
import forfun.com.guesssong.util.FLog;

/**
 * Created by huangwei05 on 16/8/22.
 */
public class TitleBar extends FrameLayout {

    private ImageView mImgBack;
    private ViewGroup mPanelCoin;
    private TextView mTxtCoin;
    private ViewGroup mPanelStage;
    private TextView mTxtStage;

    private boolean mShowBack = false;
    private boolean mShowStage = false;
    private boolean mShowCoin = false;

    public TitleBar(Context context) {
        super(context);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        FLog.i("TitleBar:");
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FLog.i("TitleBar: " + defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, R.style.AppTheme);

        mShowBack = a.getBoolean(R.styleable.TitleBar_show_back, false);
        mShowStage = a.getBoolean(R.styleable.TitleBar_show_stage, false);
        mShowCoin = a.getBoolean(R.styleable.TitleBar_show_coin, false);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.top_bar, this);

        mImgBack = (ImageView) findViewById(R.id.img_back);
        mImgBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)mImgBack.getContext()).finish();
            }
        });
        mImgBack.setVisibility(mShowBack ? View.VISIBLE : View.INVISIBLE);

        mPanelStage = (ViewGroup) findViewById(R.id.panel_game_level);
        mPanelStage.setVisibility(mShowStage ? View.VISIBLE : View.INVISIBLE);
        mTxtStage = (TextView) mPanelStage.findViewById(R.id.txt_current_stage);

        mPanelCoin = (ViewGroup) findViewById(R.id.topbar_right_panel);
        mPanelCoin.setVisibility(mShowCoin ? View.VISIBLE : View.GONE);
        mTxtCoin = (TextView) mPanelCoin.findViewById(R.id.current_coin_txt);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        FLog.i("TitleBar: " + defStyleAttr + " " + defStyleRes);

    }


    public void updateStage(String stage) {
        mTxtStage.setText(stage);
    }

    public void updateCoin(String coin) {
        mTxtCoin.setText(coin);
    }
}
