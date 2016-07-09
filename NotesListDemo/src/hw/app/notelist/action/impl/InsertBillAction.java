package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.activity.BillEditActivity;
import hw.app.notelist.utils.NoteListConstant;

public class InsertBillAction extends BaseAction {

    public InsertBillAction(BaseActivity activity) {
        super(activity, NoteListConstant.RESULT_GET_BILL);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        startActivityForResult(BillEditActivity.class);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "InserBillAction";
    }

}
