package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.presenter.NoteEditPresenter.INoteEditActivityPresenter;

public class InsertTextAction extends BaseAction {

    private INoteEditActivityPresenter mListener;
    
    public InsertTextAction(INoteEditActivityPresenter listener){
        super();
        mListener = listener;
    }
    
    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        mListener.onAddText("");
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "InserTextAction";
    }

}
