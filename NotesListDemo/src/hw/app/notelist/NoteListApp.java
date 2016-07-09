package hw.app.notelist;

import hw.app.notelist.manager.NoteSyncManager;
import android.app.Application;

public class NoteListApp extends Application {

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        NoteSyncManager.getInstance().init(this);
    }
}
