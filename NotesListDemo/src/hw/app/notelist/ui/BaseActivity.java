package hw.app.notelist.ui;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.utils.NoteListLog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

/**
 * @author huangwei05
 * 
 */
public abstract class BaseActivity extends Activity implements OnClickListener, OnItemClickListener,
        OnItemLongClickListener {
    private final String TAG = "BaseActivity";

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate()
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NoteListLog.v(TAG, getName() + " : onCreate");
        if (getLayoutId() > 0) {
            setContentView(getLayoutId());
            initView(savedInstanceState);
            initLinstener();
        }
        
        if (getPresenter() != null) {
            getPresenter().present(this, getIntent());
        }
    }

    protected void addViewClickAction(View view, BaseAction action) {
        NoteListLog.v(TAG, "addViewClickAction :" + " view : " + view + " action : " + action.getName());
        if (getPresenter() != null) {
            getPresenter().registeAction(view.getId(), action);
        }
        view.setOnClickListener(this);
    }

    protected void addMenuClickAction(int id, BaseAction action) {
        NoteListLog.v(TAG, "addMenuClickAction :" + " id : " + id + " action : " + action.getName());
        if (getPresenter() != null) {
            getPresenter().registeAction(id, action);
        }
    }

    protected void addItemClickAction(AdapterView<?> adapterView) {
        NoteListLog.v(TAG, "addItemClickAction :" + " adapterView : " + adapterView);
        adapterView.setOnItemClickListener(this);
    }

    protected void addItemLongClickAction(AdapterView<?> adapterView) {
        NoteListLog.v(TAG, "addItemClickAction :" + " adapterView : " + adapterView);
        adapterView.setOnItemLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        NoteListLog.v(TAG, "onClick :" + " v : " + v);
        if (getPresenter() != null) {
            getPresenter().handleAction(v.getId());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        NoteListLog.v(TAG, "onItemClick :" + " arg0 : " + arg0 + " arg1 : " + arg1 + " arg2 : " + arg2 + " arg3 : "
                + arg3);
        if (getPresenter() != null) {
            getPresenter().handleItemClickAction(arg0, arg1, arg2, arg3);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, "onItemLongClick :" + " arg0 : " + arg0 + " arg1 : " + arg1 + " arg2 : " + arg2 + " arg3 : "
                + arg3);
        if (getPresenter() != null) {
            return getPresenter().handleItemLongClickAction(arg0, arg1, arg2, arg3);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        NoteListLog.v(TAG, getName() + " : onResume");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        NoteListLog.v(TAG, getName() + " : onDestroy");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NoteListLog.v(TAG, getName() + " : onOptionsItemSelected : " + item.getTitle());
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }

        if (getPresenter() != null) {
            return getPresenter().handleAction(item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onBackPressed");
        if (getPresenter() != null) {
            if (getPresenter().handleAction(android.R.id.home)) {
                return;
            }
        }
        super.onBackPressed();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        NoteListLog.i(TAG, getName() + " : onActivityResult : requestCode = " + requestCode + " resultCode = "
                + resultCode);
        if (getPresenter() != null) {
            if(getPresenter().handleActionResult(requestCode,resultCode,data)){
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        NoteListLog.v(TAG, getName() + " : onConfigurationChanged");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        NoteListLog.v(TAG, getName() + " : onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        NoteListLog.v(TAG, getName() + " : onStop");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onLowMemory()
     */
    @Override
    public void onLowMemory() {
        // TODO Auto-generated method stub
        super.onLowMemory();
        NoteListLog.v(TAG, getName() + " : onLowMemory");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onNewIntent(android.content.Intent)
     */
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        NoteListLog.v(TAG, getName() + " : onNewIntent");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        NoteListLog.v(TAG, getName() + " : onPause");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onRestoreInstanceState(savedInstanceState);
        NoteListLog.v(TAG, getName() + " : onRestoreInstanceState");
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(savedInstanceState);
        NoteListLog.v(TAG, getName() + " : onSaveInstanceState");
    }

    public void startActivityForResultSafely(Intent intent, int requestCode) {
        try {
            startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startActivitySafely(Intent intent) {
        try {
            startActivity(intent);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void startActivitySafely(Context context, Class<?> clazz) {
        try {
            startActivity(new Intent(context, clazz));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public abstract String getName();

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initLinstener();

    public abstract BasePresenter getPresenter();
}
