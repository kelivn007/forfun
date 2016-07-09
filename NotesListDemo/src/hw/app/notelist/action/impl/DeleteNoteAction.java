package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.manager.NoteSyncManager;
import hw.app.notelist.ui.BasePresenter;

public class DeleteNoteAction extends BaseAction {

    private long mNoteId;

    public DeleteNoteAction(long id, BasePresenter basePresenter) {
        super(basePresenter);
        mNoteId = id;
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        NoteSyncManager.getInstance().deleteNote(mNoteId, mBasePresenter);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "DeleteNoteAction";
    }

}
