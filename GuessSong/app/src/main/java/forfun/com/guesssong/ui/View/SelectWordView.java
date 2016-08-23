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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import forfun.com.guesssong.R;
import forfun.com.guesssong.model.ISelectWordListener;
import forfun.com.guesssong.model.bean.SelectWord;

/**
 * Created by huangwei05 on 16/7/21.
 */
public class SelectWordView extends GridView {

    private ArrayList<SelectWord> mSelectWordArr = new ArrayList<SelectWord>();
    private Context mContext;
    private WordAdapter mAdapter;
    private ISelectWordListener mSelectWordLinstener;

    public SelectWordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mAdapter = new WordAdapter();
        setAdapter(mAdapter);
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

    private class WordAdapter extends BaseAdapter implements OnClickListener {

        @Override
        public int getCount() {
            return mSelectWordArr.size();
        }

        @Override
        public Object getItem(int position) {
            return mSelectWordArr.get(position);
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
                Button btnWord = (Button) convertView.findViewById(R.id.btn_word);
                btnWord.setOnClickListener(this);
            }

            Button btnWord = (Button) convertView.findViewById(R.id.btn_word);
            btnWord.setText(selectWord.mWord);
            selectWord.mSelectWordBtn = btnWord;

            btnWord.setVisibility(View.VISIBLE);

            Animation scaleAnim = AnimationUtils.loadAnimation(mContext, R.anim.common_scale);
            scaleAnim.setStartOffset(position * 50);
            convertView.startAnimation(scaleAnim);

            convertView.setTag(selectWord);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (null != mSelectWordLinstener) {
                SelectWord selectWord = (SelectWord) ((ViewGroup) v.getParent()).getTag();
                selectWord.mSelectWordBtn = (Button) v;
                mSelectWordLinstener.onWordSelect(selectWord);
            }
        }
    }
}
