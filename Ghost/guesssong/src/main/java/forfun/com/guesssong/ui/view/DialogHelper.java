/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package forfun.com.guesssong.ui.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import forfun.com.guesssong.R;

/**
 * Created by huangwei05 on 16/7/25.
 */
public class DialogHelper implements View.OnClickListener {
    private static final int DEFAULT_LAYOUT_ID = R.layout.dialog_layout;
    private AlertDialog dialog;
    private TextView mDialogContentTxt;
    private ImageButton mDialogCancelBtn;
    private ImageButton mDialogConfirmBtn;

    private onDialogClickListener mListener;

    public interface onDialogClickListener {
        public void onClick(boolean confirm);
    }

    public static DialogHelper create(Context context, String content) {
        return new DialogHelper(context, content, DEFAULT_LAYOUT_ID);
    }

    private DialogHelper(Context context, String content, int layout) {
        ViewGroup rootLayout = (ViewGroup) LayoutInflater.from(context).inflate(layout, null);
        mDialogContentTxt = (TextView) rootLayout.findViewById(R.id.dialog_content_txt);
        mDialogContentTxt.setText(content);
        mDialogCancelBtn = (ImageButton) rootLayout.findViewById(R.id.dialg_cancel_btn);
        mDialogCancelBtn.setOnClickListener(this);
        mDialogConfirmBtn = (ImageButton) rootLayout.findViewById(R.id.dialg_confirm_btn);
        mDialogConfirmBtn.setOnClickListener(this);

        dialog = new AlertDialog.Builder(context).setView(rootLayout).create();
    }

    @Override
    public void onClick(View v) {
        if (dialog.isShowing()) {
            dialog.cancel();
        }

        if (null != mListener) {
            mListener.onClick(v.getId() == R.id.dialg_confirm_btn ? true : false);
        }
    }

    public DialogHelper setOnDialogClickListener(onDialogClickListener listener) {
        mListener = listener;
        return this;
    }

    public void show() {
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }
}
