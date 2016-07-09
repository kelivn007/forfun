package hw.app.notelist.ui;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.utils.NoteListLog;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.baidu.xcloud.plugincloudnote.bean.ErrorBean;
import com.baidu.xcloud.plugincloudnote.bean.ResultBean;
import com.baidu.xcloud.plugincloudnote.interf.NoteOPListener;
import com.baidu.xcloud.plugincloudnote.note.SimpleNote;
import com.baidu.xcloud.plugincloudnote.note.XCLNote;

public abstract class BasePresenter implements NoteOPListener {

    public static final String TAG = "BasePresenter";
    protected HashMap<Integer, BaseAction> mActionMap = new HashMap<Integer, BaseAction>();

    public void registeAction(int id, BaseAction action) {
        if (!mActionMap.containsKey(id)) {
            mActionMap.put(id, action);
        }
    }

    public boolean handleAction(int id) {
        BaseAction action = mActionMap.get(id);
        if (action != null) {
            NoteListLog.v(TAG, action.getName() + " : handleAction");
            return action.execute();
        }

        return false;
    }

    public boolean handleActionResult(int requestCode, int resultCode, Intent data) {
        NoteListLog.v(TAG, getName() + " : handleAction requestCode : " + requestCode + " resultCode : " + resultCode
                + " Intent : " + data);
        return false;
    }

    public void handleItemClickAction(AdapterView<?> adapterView, View view, int id, long index) {
        OnItemClickListener listener = getItemClickListener();
        NoteListLog.v(TAG, getName() + " : handleItemClickAction :  adapterView : " + adapterView + " view : " + view
                + " id : " + id + " index : " + index + " listener : " + listener);
        if (listener != null) {
            listener.onItemClick(adapterView, view, id, index);
        }
    }

    public boolean  handleItemLongClickAction(AdapterView<?> adapterView, View view, int id, long index) {
        OnItemLongClickListener listener = getItemLongClickListener();
        NoteListLog.v(TAG, getName() + " : handleItemClickAction :  adapterView : " + adapterView + " view : " + view
                + " id : " + id + " index : " + index + " listener : " + listener);
        if (listener != null) {
            return listener.onItemLongClick(adapterView, view, id, index);
        }
        
        return false;
    }

    @Override
    public void onClearLocalNotes(ResultBean arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onClearLocalNotes : " + arg0);
    }

    @Override
    public void onDeleteResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onDeleteResult : " + arg0);
    }

    @Override
    public void onError(ErrorBean arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onError : " + arg0);
    }

    @Override
    public void onGetAllNoteResult(List<SimpleNote> arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onGetAllNoteResult : " + arg0);
    }

    @Override
    public void onGetNoteResult(XCLNote arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onGetNoteResult : " + arg0);
    }

    @Override
    public void onInsertResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onInsertResult : " + arg0);
    }

    @Override
    public void onModifyResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onModifyResult : " + arg0);
    }

    @Override
    public void onXcloudError(int arg0) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onXcloudError : " + arg0);
    }

    public void onCancel() {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onCancel ");
    }

    public abstract String getName();

    public abstract void present(BaseActivity activity, Intent intent);

    public abstract OnItemClickListener getItemClickListener();

    public abstract OnItemLongClickListener getItemLongClickListener();
}
