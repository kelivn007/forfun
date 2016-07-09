/**
 * 
 */
package hw.app.notelist.ui.activity;

import hw.app.notelist.utils.NoteListConstant;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;


/**
 * @author huangwei05
 *
 */
public class NavigateActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		DisplayMetrics displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		editor.putInt(NoteListConstant.SCREEN_WIDTH, displayMetrics.widthPixels);
		editor.putInt(NoteListConstant.SCREEN_HEIGHT, displayMetrics.heightPixels);
		editor.commit();

		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

}
