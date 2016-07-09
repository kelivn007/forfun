package hw.app.notelist.widget;

import hw.app.notelist.R;
import hw.app.notelist.manager.INoteSyncable;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ToggleButton;

import com.baidu.xcloud.plugincloudnote.note.XCLNote;
import com.baidu.xcloud.plugincloudnote.note.content.BaseContent;
import com.baidu.xcloud.plugincloudnote.note.content.RichContent;

public class NoteView extends ScrollView implements INoteSyncable, INoteView {
    private LayoutInflater mLayoutInflater;
    private ViewGroup mEditorContainer;
    private EditText mTitleEditText;
    private ToggleButton mImportancBtn;
    private RichEditor mRichEditor;
    private XCLNote mNote;

    public NoteView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public NoteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public NoteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        mLayoutInflater = LayoutInflater.from(getContext());
        mEditorContainer = (ViewGroup) mLayoutInflater.inflate(R.layout.note_view, this);
        mTitleEditText = (EditText) mEditorContainer.findViewById(R.id.note_title_edit);
        mImportancBtn = (ToggleButton) mEditorContainer.findViewById(R.id.note_important_btn);
        mRichEditor = (RichEditor) mEditorContainer.findViewById(R.id.note_body_container);
        mNote = new XCLNote();
    }

    @Override
    public XCLNote genXCLNote() {
        // TODO Auto-generated method stub
        mNote.setContent(mRichEditor.getContent());
        mNote.setTitle(mTitleEditText.getText().toString());
        mNote.setImportant(mImportancBtn.isChecked() ? 1 : 0);
        mNote.setRepeat(false);
        return mNote;
    }

    @Override
    public long getNoteId() {
        // TODO Auto-generated method stub
        return mNote.getID();
    }

    @Override
    public void setXCLNote(XCLNote note) {
        mNote = note;
        mTitleEditText.setText(note.getTitle());
        mImportancBtn.setChecked(note.getImportant() > 0 ? true : false);
        if (note.getContentType() == BaseContent.RICH_CONTENT) {
            mRichEditor.setRichContent((RichContent) note.getContent());
        }
    }

    @Override
    public void addTextElement(String text) {
        mRichEditor.addTextElement(text);
    }

    @Override
    public void addImageElement(String path) {
        mRichEditor.addImageElement(path);
    }

    @Override
    public void addAudioElement(String path) {
        // TODO Auto-generated method stub
        mRichEditor.addAudioElement(path);
    }

    @Override
    public void addVideoElement(String path) {
        // TODO Auto-generated method stub
        mRichEditor.addVideoElement(path);
    }
}
