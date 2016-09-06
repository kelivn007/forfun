/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import forfun.com.guesssong.util.Scalpel;

/**
 * Created by huangwei05 on 16/9/6.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        Scalpel.bindView(this);

        init();
    }

    @Override
    public void onClick(View v) {
        dispatchClick(v.getId());
    }

    protected abstract int getLayoutId();

    protected void init() {

    }

    protected abstract void dispatchClick(int id);
}
