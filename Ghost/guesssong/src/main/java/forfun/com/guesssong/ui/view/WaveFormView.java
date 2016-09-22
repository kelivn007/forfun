/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import forfun.com.guesssong.util.FLog;

/**
 * Created by huangwei05 on 16/9/9.
 */
public class WaveFormView extends View{

    private Paint mPaint = new Paint();
    private Path mWavePath = new Path();
    private byte[] mWaveFormArr;

    public WaveFormView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setColor(getResources().getColor(android.R.color.holo_red_light));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(2.0f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void updateWaveForm(byte[] form) {
        mWaveFormArr = form;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(getResources().getColor(android.R.color.white));

        mWavePath.reset();

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int middle = height / 2;

        mWavePath.moveTo(0, middle);

        if (null != mWaveFormArr) {
            float yIncrease = height * 1.0f / 256;
            float xIncrease = width * 1.0f / mWaveFormArr.length;


            for (int i = 0; i < mWaveFormArr.length; i++) {
                float nextY = mWaveFormArr[i] > 0 ? height - (mWaveFormArr[i] * yIncrease)
                        : -(mWaveFormArr[i] * yIncrease);
                mWavePath.lineTo(xIncrease * i, nextY);
            }
        }

        mWavePath.lineTo(width, middle);
        canvas.drawPath(mWavePath, mPaint);
    }
}
