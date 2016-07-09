package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.manager.NoteAccountManager;
import hw.app.notelist.manager.NoteAccountManager.ILoginListener;
import hw.app.notelist.ui.BaseActivity;

public class StartLogoutAction extends BaseAction {

    private ILoginListener mListener;

    public StartLogoutAction(BaseActivity activity, ILoginListener listener) {
        super(activity);
        mListener = listener;
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        NoteAccountManager.logout(mBaseActivity, mListener);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "StartLogoutAction";
    }

}
