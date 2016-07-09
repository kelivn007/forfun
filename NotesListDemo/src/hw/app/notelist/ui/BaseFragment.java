/**
 * 
 */
package hw.app.notelist.ui;

import hw.app.notelist.utils.NoteListLog;

import android.R.integer;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

/**
 * @author huangwei05
 *
 */
public abstract class BaseFragment extends Fragment {
	private static final String TAG = "BaseFragment";
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityCreated(android.os.Bundle)
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		NoteListLog.v(TAG, getName() + " : onActivityCreated");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		NoteListLog.v(TAG, getName() + " : onCreate");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateView(android.os.Bundle)
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		NoteListLog.v(TAG, getName() + " : onCreateView");
		return null;
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onCreateOptionsMenu(android.view.Menu, android.view.MenuInflater)
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		NoteListLog.v(TAG, getName() + " : onCreateOptionsMenu");
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onOptionsItemSelected(android.view.MenuItem)
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		NoteListLog.v(TAG, getName() + " : onOptionsItemSelected " + item.getTitle());
		return false;
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		NoteListLog.v(TAG, getName() + " : onDestroy");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		NoteListLog.v(TAG, getName() + " : onLowMemory");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		NoteListLog.v(TAG, getName() + " : onPause");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		NoteListLog.v(TAG, getName() + " : onResume");
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		NoteListLog.v(TAG, getName() + " : onSaveInstanceState");
	}

	/* (non-Javadoc)
	 * @see android.app.Fragment#onTrimMemory(int)
	 */
	@Override
	public void onTrimMemory(int level) {
		// TODO Auto-generated method stub
		super.onTrimMemory(level);
		NoteListLog.v(TAG, getName() + " : onTrimMemory");
	}
	
	/* (non-Javadoc)
	 * @see android.app.Fragment#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		NoteListLog.i(TAG, getName() + " : onActivityResult : requestCode = " + requestCode + " resultCode = " + resultCode);
	}
	
	public void startActivityForResultSafely(Intent intent,int requestCode){
		try {
			startActivityForResult(intent, requestCode);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void startActivitySafely(Intent intent){
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
}
