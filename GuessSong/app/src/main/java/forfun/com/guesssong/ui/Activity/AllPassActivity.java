/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import forfun.com.guesssong.R;

/**
 * Created by huangwei05 on 16/7/25.
 */
public class AllPassActivity extends Activity {

    private ViewGroup mTopBarRightPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_pass_layout);

        mTopBarRightPanel = (ViewGroup) findViewById(R.id.topbar_right_panel);
        mTopBarRightPanel.setVisibility(View.GONE);
    }
}
