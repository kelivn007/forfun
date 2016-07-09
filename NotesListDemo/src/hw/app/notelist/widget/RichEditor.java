package hw.app.notelist.widget;

import hw.app.notelist.R;
import hw.app.notelist.ui.activity.ImageEditActivity;
import hw.app.notelist.utils.ImageUtils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baidu.xcloud.plugincloudnote.note.content.RichContent;
import com.baidu.xcloud.plugincloudnote.note.content.element.AudioElement;
import com.baidu.xcloud.plugincloudnote.note.content.element.BaseElement;
import com.baidu.xcloud.plugincloudnote.note.content.element.ImageElement;
import com.baidu.xcloud.plugincloudnote.note.content.element.TextElement;
import com.baidu.xcloud.plugincloudnote.note.content.element.VideoElement;

public class RichEditor extends LinearLayout implements OnClickListener, OnLongClickListener {
    private final String TAG = "RichEditor";
    private final String ELEMENT_TYPE = "type";
    private final String ELEMENT_DATA = "data";
    private static final CharSequence SPACE = " ";

    public RichEditor(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public RichEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public RichEditor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public RichContent getContent() {
        RichContent richContent = new RichContent();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Bundle bundle = (Bundle) child.getTag();
            if (bundle != null) {
                if (BaseElement.TEXT_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                    TextElement element = new TextElement();
                    element.setText(((EditText) child).getText().toString());
                    richContent.getElementList().add(element);
                }

                if (BaseElement.IMAGE_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                    ImageElement element = new ImageElement();
                    element.setPath(bundle.getString(ELEMENT_DATA));
                    richContent.getElementList().add(element);
                }

                if (BaseElement.AUDIO_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                    AudioElement element = new AudioElement();
                    element.setPath(bundle.getString(ELEMENT_DATA));
                    richContent.getElementList().add(element);
                }

                if (BaseElement.VIDEO_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                    VideoElement element = new VideoElement();
                    element.setPath(bundle.getString(ELEMENT_DATA));
                    richContent.getElementList().add(element);
                }
            }
        }

        return richContent;
    }

    public void setRichContent(RichContent richContent) {
        removeAllViews();
        for (BaseElement element : richContent.getElementList()) {
            if (element instanceof TextElement) {
                addTextElement(((TextElement) element).getText());
            }

            if (element instanceof ImageElement) {
                addImageElement(((ImageElement) element).getPath());
            }

            if (element instanceof AudioElement) {
                addAudioElement(((AudioElement) element).getPath());
            }

            if (element instanceof VideoElement) {
                addVideoElement(((VideoElement) element).getPath());
            }
        }
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        Bundle bundle = (Bundle) view.getTag();
        if (bundle != null) {
            if (BaseElement.IMAGE_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                Intent intent = new Intent(getContext(), ImageEditActivity.class);
                intent.putExtra(ImageEditActivity.IMAGE_PATH, bundle.getString(ELEMENT_DATA));
                getContext().startActivity(intent);
            }

            if (BaseElement.AUDIO_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String type = "audio/*";
                Uri name = Uri.fromFile(new File(bundle.getString(ELEMENT_DATA)));
                intent.setDataAndType(name, type);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

            if (BaseElement.VIDEO_ELEMENT.equals(bundle.getString(ELEMENT_TYPE))) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String type = "video/*";
                Uri name = Uri.fromFile(new File(bundle.getString(ELEMENT_DATA)));
                intent.setDataAndType(name, type);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }

        }
    }

    @Override
    public boolean onLongClick(View view) {
        // TODO Auto-generated method stub
        Bundle bundle = (Bundle) view.getTag();
        if (bundle != null) {
            removeView(view);
            return true;
        }

        return false;
    }

    public void addTextElement(String text) {
        final EditText editText = new EditText(getContext());
        editText.setText(text);
        editText.getText().insert(0, SPACE);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                // NoteListLog.i(TAG, "onTextChanged ");
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                // NoteListLog.i(TAG, "beforeTextChanged ");
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                // NoteListLog.i(TAG, "afterTextChanged ");
                if (TextUtils.isEmpty(arg0)) {
                    removeView(editText);
                }
            }
        });

        Bundle bundle = new Bundle();
        bundle.putString(ELEMENT_TYPE, BaseElement.TEXT_ELEMENT);
        editText.setTag(bundle);

        editText.requestFocus();
        addView(editText);
    }

    public void addImageElement(String path) {
        ImageView imageView = new ImageView(getContext());
        LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(5, 5, 5, 5);
        imageView.setLayoutParams(layoutParams);
        imageView.setBackgroundDrawable(new BitmapDrawable(ImageUtils.getBitmapFromPath(path)));

        imageView.setOnClickListener(this);
        imageView.setOnLongClickListener(this);

        Bundle bundle = new Bundle();
        bundle.putString(ELEMENT_TYPE, BaseElement.IMAGE_ELEMENT);
        bundle.putString(ELEMENT_DATA, path);
        imageView.setTag(bundle);

        addView(imageView);
    }

    public void addAudioElement(String path) {
        Button button = new Button(getContext());
        button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        button.setBackgroundResource(R.drawable.btn_bg);

        File file = new File(path);
        if (file.exists()) {
            button.setText("录音文件");
        } else {
            button.setText("录音文件丢失");
        }

        Bundle bundle = new Bundle();
        bundle.putString(ELEMENT_TYPE, BaseElement.AUDIO_ELEMENT);
        bundle.putString(ELEMENT_DATA, path);
        button.setTag(bundle);

        button.setOnLongClickListener(this);
        button.setOnClickListener(this);

        addView(button);
    }

    public void addVideoElement(String path) {
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setMinimumWidth(100);
        imageView.setMinimumHeight(100);

        Bitmap bitmap = ImageUtils.createVideoThumbnail(path);
        if (bitmap == null) {
            imageView.setBackgroundResource(R.drawable.preview);
        } else {
            imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            imageView.setImageResource(R.drawable.ic_menu_play_clip);
        }

        Bundle bundle = new Bundle();
        bundle.putString(ELEMENT_TYPE, BaseElement.VIDEO_ELEMENT);
        bundle.putString(ELEMENT_DATA, path);
        imageView.setTag(bundle);

        imageView.setOnLongClickListener(this);

        imageView.setOnClickListener(this);
        addView(imageView);
    }
}
