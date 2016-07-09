package hw.app.notelist.action.impl;

import android.content.Intent;
import android.provider.MediaStore;
import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.utils.NoteListConstant;

public class InsertVideoAction extends BaseAction {

    public InsertVideoAction(BaseActivity activity) {
        super(activity, NoteListConstant.RESULT_GET_VIDEO);
     }
    
    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        Intent videoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(videoIntent);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
