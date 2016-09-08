/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import forfun.com.guesssong.R;
import forfun.com.guesssong.model.ISelectWordListener;
import forfun.com.guesssong.model.bean.SelectWord;
import forfun.com.guesssong.util.FLog;

/**
 * Created by huangwei05 on 16/7/21.
 */
public class SelectWordView extends GridView {

    private ArrayList<SelectWord> mSelectWordArr = new ArrayList<SelectWord>();
    private Context mContext;
    private WordAdapter mAdapter;
    private ISelectWordListener mSelectWordLinstener;
    private GridLayoutAnimationController mGridLayoutAnimationController;

    public SelectWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mAdapter = new WordAdapter();
        setAdapter(mAdapter);
        Animation scaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.common_scale);
        mGridLayoutAnimationController = new GridLayoutAnimationController(scaleAnim);
        mGridLayoutAnimationController.setColumnDelay(0.5f);
        mGridLayoutAnimationController.setRowDelay(0.3f);
        mGridLayoutAnimationController.setDirection(GridLayoutAnimationController.DIRECTION_TOP_TO_BOTTOM);
        setLayoutAnimation(mGridLayoutAnimationController);
        startLayoutAnimation();
    }

    public void setSelectListener(ISelectWordListener listener) {
        mSelectWordLinstener = listener;
    }

    public void updateData(String[] words) {
        mSelectWordArr.clear();
        for (int i = 0; i < 18; i++) {
            mSelectWordArr.add(new SelectWord(words[i]));
        }

        mAdapter.notifyDataSetChanged();
    }

    public void resetWordBtn(String word) {
        FLog.i("reset word " + word);
        for (SelectWord selectword : mSelectWordArr) {
            if (word.equalsIgnoreCase(selectword.mWord)) {
                selectword.mSelectWordBtn.setVisibility(View.VISIBLE);
                return;
            }
        }
    }

    private class WordAdapter extends BaseAdapter implements OnClickListener {

        @Override
        public int getCount() {
            return mSelectWordArr.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SelectWord selectWord = mSelectWordArr.get(position);

            if (null == convertView) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.input_item, null);
                Button btn = (Button) convertView.findViewById(R.id.btn_word);
                btn.setOnClickListener(this);
            }

            Button btn = (Button) convertView.findViewById(R.id.btn_word);
            btn.setText(selectWord.mWord);
            btn.setVisibility(View.VISIBLE);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public void onClick(View v) {
            if (null != mSelectWordLinstener) {
                FLog.i("onclick " + v);

                Button btnWord = (Button) v;
                String word = btnWord.getText().toString();
                for (SelectWord selectword : mSelectWordArr) {
                    if (word.equalsIgnoreCase(selectword.mWord)) {
                        selectword.mSelectWordBtn = btnWord;
                        selectword.mSelectWordBtn.setVisibility(View.INVISIBLE);
                        mSelectWordLinstener.onWordSelect(selectword);
                        return;
                    }
                }
            }
        }
    }
}
