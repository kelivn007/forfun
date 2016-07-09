package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.activity.NoteEditActivity;
import hw.app.notelist.ui.presenter.NoteEditPresenter;
import android.content.Intent;

public class OpenNoteAction extends BaseAction {

    private long mNoteId;

    public OpenNoteAction(BaseActivity activity, long id) {
        super(activity);
        mNoteId = id;
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(mBaseActivity, NoteEditActivity.class);
        if (mNoteId > 0) {
            intent.putExtra(NoteEditPresenter.NOTE_ID, mNoteId);
        }
        startActivity(intent);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "NoteItemClickAction";
    }

}
