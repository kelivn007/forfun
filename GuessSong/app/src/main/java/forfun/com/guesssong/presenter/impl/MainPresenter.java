/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.presenter.impl;

/**
 * Created by huangwei05 on 16/8/14.
 */
public class MainPresenter {
    public interface IMainPresenter {
        public void showPlaySong();
    }

    private IMainPresenter mListener;

    public MainPresenter(IMainPresenter listener) {
        mListener = listener;
    }

    public void init() {

    }

    public void clickPlaySong() {

    }
}
