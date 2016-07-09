/**
 * 
 */
package hw.app.notelist.ui.activity;

import hw.app.notelist.R;
import hw.app.notelist.action.impl.InsertAudioAction;
import hw.app.notelist.action.impl.InsertBillAction;
import hw.app.notelist.action.impl.InsertPictureAction;
import hw.app.notelist.action.impl.InsertTextAction;
import hw.app.notelist.action.impl.InsertTodoAction;
import hw.app.notelist.action.impl.InsertVideoAction;
import hw.app.notelist.action.impl.SaveNoteAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.ui.presenter.NoteEditPresenter;
import hw.app.notelist.ui.presenter.NoteEditPresenter.INoteEditActivityPresenter;
import hw.app.notelist.widget.NoteView;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;

import com.baidu.xcloud.plugincloudnote.note.XCLNote;

/**
 * @author huangwei05
 * 
 */
public class NoteEditActivity extends BaseActivity implements INoteEditActivityPresenter {
    public static final String TAG = "NoteEditActivity";

    private NoteView mNoteView;
    private NoteEditPresenter mPresenter;

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TAG;
    }

    @Override
    protected int getLayoutId() {
        // TODO Auto-generated method stub
        return R.layout.note_edit_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mNoteView = (NoteView) findViewById(R.id.note_container);
        mPresenter = new NoteEditPresenter(this);
    }

    @Override
    protected void initLinstener() {
        // TODO Auto-generated method stub
        addMenuClickAction(android.R.id.home, new SaveNoteAction(mNoteView, mPresenter));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);

        addMenuClickAction(R.id.action_text, new InsertTextAction(this));
        addMenuClickAction(R.id.action_todo, new InsertTodoAction());
        addMenuClickAction(R.id.action_picture, new InsertPictureAction(this));
        addMenuClickAction(R.id.action_bill, new InsertBillAction(this));
        addMenuClickAction(R.id.action_record, new InsertAudioAction(this));
//        addMenuClickAction(R.id.action_video, new InsertVideoAction(this));
        return true;
    }

    @Override
    public BasePresenter getPresenter() {
        // TODO Auto-generated method stub
        return mPresenter;
    }

    @Override
    public void onInitNote(final XCLNote note) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mNoteView.setXCLNote(note);
            }
        });
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    @Override
    public void onAddText(final String text) {
        // TODO Auto-generated method stub
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mNoteView.addTextElement(text);
            }
        });
    }

    @Override
    public void onAddPhoto(final Uri uri) {
        // TODO Auto-generated method stub
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            final String imgPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            cursor.close();
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mNoteView.addImageElement(imgPath);
                }
            });
        }
    }

    @Override
    public void onAddAudio(Uri uri) {
        // TODO Auto-generated method stub
        Cursor actualaudiocursor = getContentResolver().query(uri, null, null, null, null);
        actualaudiocursor.moveToFirst();
        final String audioPath =
                actualaudiocursor.getString(actualaudiocursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        actualaudiocursor.close();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mNoteView.addAudioElement(audioPath);
            }
        });
    }

    @Override
    public void onAddVideo(Uri uri) {
        // TODO Auto-generated method stub
        Cursor actualvideocursor = getContentResolver().query(uri, null, null, null, null);
        actualvideocursor.moveToFirst();
        final String videoPath =
                actualvideocursor.getString(actualvideocursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
        actualvideocursor.close();
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                mNoteView.addVideoElement(videoPath);
            }
        });
    }
}
