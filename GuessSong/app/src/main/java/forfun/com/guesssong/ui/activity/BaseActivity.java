/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import forfun.com.guesssong.util.Scalpel;

/**
 * Created by huangwei05 on 16/9/6.
 */
public abstract class BaseActivity extends Activity implements View.OnClickListener{

    String[] permissions = new String[] {Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        Scalpel.bindView(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 0);
        } else {
            init();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
