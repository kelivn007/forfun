package hw.app.notelist.ui.presenter;

import hw.app.notelist.R;
import hw.app.notelist.action.BaseAction;
import hw.app.notelist.action.impl.DeleteNoteAction;
import hw.app.notelist.action.impl.OpenNoteAction;
import hw.app.notelist.manager.NoteAccountManager;
import hw.app.notelist.manager.NoteAccountManager.ILoginListener;
import hw.app.notelist.manager.NoteSyncManager;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.utils.NoteListLog;
import hw.app.notelist.utils.TimeFormatUtils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.xcloud.account.AccountInfo;
import com.baidu.xcloud.plugincloudnote.ResultCode;
import com.baidu.xcloud.plugincloudnote.bean.ResultBean;
import com.baidu.xcloud.plugincloudnote.note.SimpleNote;

public class MainPresenter extends BasePresenter implements ILoginListener {
    private static final String TAG = "MainActivityPresenter";

    public static interface IMainActivityPresenter {
        public void onShowLogin();

        public void onShowUserName(String username);

        public void onSetAdapter(BaseAdapter adapter);
    }

    public static class MainListAdapter extends BaseAdapter implements OnItemClickListener, OnItemLongClickListener {

        public List<SimpleNote> mNotes = new ArrayList<SimpleNote>();
        private LayoutInflater mInflater;
        private BaseActivity mActivity;

        public MainListAdapter(BaseActivity activity) {
            mActivity = activity;
            mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void setData(List<SimpleNote> notes) {
            mNotes = notes;
        }
        
        public void removeNote(long id){
            for (SimpleNote note : mNotes) {
                if(note.getID() == id){
                    mNotes.remove(note);
                    mActivity.runOnUiThread(new Runnable() {
                        
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            notifyDataSetChanged();
                        }
                    });
                    
                    return;
                }
            }
            
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mNotes.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return mNotes.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int index, View view, ViewGroup viewGroup) {
            // TODO Auto-generated method stub
            if (view == null) {
                view = mInflater.inflate(R.layout.main_list_item, viewGroup, false);
            }

            ImageView important = (ImageView) view.findViewById(R.id.main_list_item_important);
            TextView modtime = (TextView) view.findViewById(R.id.main_list_item_modtime);
            TextView title = (TextView) view.findViewById(R.id.main_list_item_title);
            TextView description = (TextView) view.findViewById(R.id.main_list_item_descript);

            important.setBackgroundResource(mNotes.get(index).getImportant() > 0 ? R.drawable.ic_action_important_light
                    : R.drawable.ic_action_not_important_light);

            String timeString =
                    TimeFormatUtils.TimeStamp2Date(mNotes.get(index).getLastModTime(), "yyyy-MM-dd HH:mm:ss");
            modtime.setText(timeString);

            title.setText(mNotes.get(index).getTitle());
            description.setText(mNotes.get(index).getDescription());
            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            BaseAction action = new OpenNoteAction(mActivity, mNotes.get(arg2).getID());
            action.execute();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            BaseAction action = new DeleteNoteAction(mNotes.get(arg2).getID(), mActivity.getPresenter());
            return action.execute();
        }

    }

    private IMainActivityPresenter mListener;
    private MainListAdapter mAdapter;

    public MainPresenter(IMainActivityPresenter iMainActivityPresenter) {
        mListener = iMainActivityPresenter;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return TAG;
    }

    @Override
    public void present(BaseActivity activity, Intent intent) {
        // TODO Auto-generated method stub
        String userName = NoteAccountManager.getUserName(activity);
        if (TextUtils.isEmpty(userName)) {
            mListener.onShowLogin();
        } else {
            mListener.onShowUserName(userName);
        }

        mAdapter = new MainListAdapter(activity);
        NoteSyncManager.getInstance().getAllNotes(this);
    }

    @Override
    public OnItemClickListener getItemClickListener() {
        // TODO Auto-generated method stub
        return mAdapter;
    }
    
    @Override
    public OnItemLongClickListener getItemLongClickListener() {
        // TODO Auto-generated method stub
        return mAdapter;
    }

    @Override
    public void onLogin(AccountInfo accountInfo) {
        // TODO Auto-generated method stub
        mListener.onShowUserName(accountInfo.getUid());
    }

    @Override
    public void onLogout() {
        // TODO Auto-generated method stub
        mListener.onShowLogin();
    }

    @Override
    public void onGetAllNoteResult(List<SimpleNote> arg0) {
        // TODO Auto-generated method stub
        super.onGetAllNoteResult(arg0);
        mAdapter.setData(arg0);
        mListener.onSetAdapter(mAdapter);
    }

    @Override
    public void onDeleteResult(ResultBean arg0) {
        // TODO Auto-generated method stub
        super.onDeleteResult(arg0);
        mAdapter.removeNote(arg0.noteId);
    }

    

}
