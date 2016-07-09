package hw.app.notelist.ui.presenter;

import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.utils.NoteListConstant;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.baidu.xcloud.cloudnote.CloudNoteClient;
import com.baidu.xcloud.plugincloudnote.bean.ResultBean;
import com.baidu.xcloud.plugincloudnote.note.XCLNote;

public class NoteEditPresenter extends BasePresenter {
    public static final String NOTE_ID = "note_id";

    public static interface INoteEditActivityPresenter {
        public void onInitNote(XCLNote note);

        public void onAddText(String text);

        public void onAddPhoto(Uri uri);

        public void onAddAudio(Uri uri);
        
        public void onAddVideo(Uri uri);

        public void onFinish();
    }

    private INoteEditActivityPresenter mListener;
    private long mNoteID;

    public NoteEditPresenter(INoteEditActivityPresenter listener) {
        mListener = listener;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "NoteEditActivityPresenter";
    }

    @Override
    public void present(BaseActivity activity, Intent intent) {
        // TODO Auto-generated method stub
        mNoteID = intent.getLongExtra(NOTE_ID, -1);
        if (mNoteID >= 0) {
            CloudNoteClient.getInstance().getNote(mNoteID, this);
        }
    }

    @Override
    public boolean handleActionResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        boolean ret = super.handleActionResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            ret = true;
            switch (requestCode) {
                case NoteListConstant.RESULT_GET_PHOTO:
                    if (data != null) {
                        Uri uri = data.getData();
                        mListener.onAddPhoto(uri);
                    }
                    break;
                case NoteListConstant.RESULT_GET_AUDIO:
                    if (data != null) {
                        Uri uri = data.getData();
                        mListener.onAddAudio(uri);
                    }
                    break;
                case NoteListConstant.RESULT_GET_VIDEO:
                    if (data != null) {
                        Uri uri = data.getData();
                        mListener.onAddVideo(uri);
                    }
                    break;
                default:
                    break;
            }

        }
        return ret;
    }

    @Override
    public OnItemClickListener getItemClickListener() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onGetNoteResult(XCLNote note) {
        // TODO Auto-generated method stub
        super.onGetNoteResult(note);
        mListener.onInitNote(note);
    }

    @Override
    public void onInsertResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        super.onInsertResult(arg0);
        mListener.onFinish();
    }

    @Override
    public void onModifyResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        super.onModifyResult(arg0);
        mListener.onFinish();
    }

    @Override
    public void onCancel() {
        // TODO Auto-generated method stub
        super.onCancel();
        mListener.onFinish();
    }

    @Override
    public OnItemLongClickListener getItemLongClickListener() {
        // TODO Auto-generated method stub
        return null;
    }

}
