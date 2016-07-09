package hw.app.notelist.action.impl;

import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.utils.NoteListConstant;
import android.content.Intent;
import android.provider.MediaStore;

public class InsertPictureAction extends BaseAction {
    
    public InsertPictureAction(BaseActivity activity) {
       super(activity, NoteListConstant.RESULT_GET_PHOTO);
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        Intent photoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoIntent);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "InserPictureAction";
    }

}
