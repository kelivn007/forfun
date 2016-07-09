package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.manager.INoteSyncable;
import hw.app.notelist.manager.NoteSyncManager;
import hw.app.notelist.ui.BasePresenter;

public class SaveNoteAction extends BaseAction {

    public SaveNoteAction(INoteSyncable syncable, BasePresenter presenter) {
        super(syncable, presenter);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        NoteSyncManager.getInstance().saveNote(mINoteSyncable, mBasePresenter);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "SaveNoteAction";
    }

}
