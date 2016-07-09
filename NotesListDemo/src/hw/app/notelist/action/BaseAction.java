package hw.app.notelist.action;

import hw.app.notelist.manager.INoteSyncable;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import android.content.Intent;

public abstract class BaseAction {
    protected BaseActivity mBaseActivity;
    protected int mCode;
    protected BasePresenter mBasePresenter;
    protected INoteSyncable mINoteSyncable;

    public BaseAction() {
    }

    public BaseAction(BaseActivity activity) {
        mBaseActivity = activity;
    }

    public BaseAction(BaseActivity activity, int code) {
        mBaseActivity = activity;
        mCode = code;
    }

    public BaseAction(INoteSyncable syncable) {
        mINoteSyncable = syncable;
    }

    public BaseAction(BasePresenter presenter) {
        mBasePresenter = presenter;
    }
    
    public BaseAction(INoteSyncable syncable, BasePresenter presenter) {
        mBasePresenter = presenter;
        mINoteSyncable = syncable;
    }

    protected void startActivity(Class<?> clazz) {
        mBaseActivity.startActivitySafely(mBaseActivity, clazz);
    }

    protected void startActivity(Intent intent) {
        mBaseActivity.startActivitySafely(intent);
    }

    protected void startActivityForResult(Class<?> clazz) {
        Intent intent = new Intent(mBaseActivity, clazz);
        startActivityForResult(intent);
    }

    protected void startActivityForResult(Intent intent) {
        mBaseActivity.startActivityForResultSafely(intent, mCode);
    }

    public abstract boolean execute();

    public abstract String getName();
}
