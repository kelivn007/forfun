package hw.app.notelist.action.impl;

import android.content.Intent;
import android.provider.MediaStore;
import hw.app.notelist.action.BaseAction;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.utils.NoteListConstant;

public class InsertAudioAction extends BaseAction {

    public InsertAudioAction(BaseActivity activity) {
        super(activity, NoteListConstant.RESULT_GET_AUDIO);
     }
    
    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        Intent audioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(audioIntent);
        return true;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
