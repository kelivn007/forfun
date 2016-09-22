/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.presenter;

import android.content.AsyncTaskLoader;
import android.content.Context;
import forfun.com.guesssong.util.FLog;

/**
 * Created by huangwei05 on 16/8/15.
 */
public class PresenterLoader<T extends BasePresenter> extends AsyncTaskLoader<T> {

    private Class<T> mPresenterClass;
    private T mPresenter;

    public PresenterLoader(Context context, Class<T> clazz) {
        super(context);
        mPresenterClass = clazz;
    }

    @Override
    public T loadInBackground() {
        FLog.i("loadInBackgroud");
        try {
            mPresenter = mPresenterClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return mPresenter;
    }

    @Override
    protected void onReset() {
        FLog.i("onReset");
        super.onReset();
    }

    @Override
    protected void onForceLoad() {
        FLog.i("onForceLoad");
        super.onForceLoad();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        FLog.i("onStartLoading");
        if (null != mPresenter) {
            deliverResult(mPresenter);
        } else {
            forceLoad();
        }
    }
}
