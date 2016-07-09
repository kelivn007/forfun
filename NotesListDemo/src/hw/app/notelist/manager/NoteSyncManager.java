package hw.app.notelist.manager;

import hw.app.notelist.ui.BasePresenter;
import android.content.Context;

import com.baidu.xcloud.cloudnote.CloudNoteClient;

/**
 * @author huangwei05
 * 
 */
public class NoteSyncManager {
    private final String TAG = "NoteSyncManager";

    private static NoteSyncManager sNoteSyncManager;

    private Context mContext;
    private CloudNoteClient mClient;

    private NoteSyncManager() {
        mClient = CloudNoteClient.getInstance();
    }

    public static NoteSyncManager getInstance() {
        synchronized (NoteSyncManager.class) {
            if (sNoteSyncManager == null) {
                sNoteSyncManager = new NoteSyncManager();
            }
        }
        return sNoteSyncManager;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mClient.init(mContext, null);
    }

    public void getAllNotes(BasePresenter basePresenter) {
        mClient.getAllNotes(20, basePresenter);
    }

    public void saveNote(INoteSyncable inote, BasePresenter basePresenter) {
        if(inote.getNoteId() > 0){
            mClient.modifyNote(inote.genXCLNote(), basePresenter);
        }else{
            mClient.insertNote(inote.genXCLNote(), basePresenter);
        }
    }
    
    public void deleteNote(long id, BasePresenter basePresenter) {
        mClient.deleteNote(id, basePresenter);
    }
}
