/**
 * 
 */
package hw.app.notelist.utils;

import android.util.Log;

/**
 * @author huangwei05
 *
 */
public class NoteListLog {
	private static final String TAG = "NoteListLog";

	private static final boolean IS_VERBOSE = true;
	private static final boolean IS_DEBUG = true;
	private static final boolean IS_INFO = true;
	private static final boolean IS_WARNING = true;
	private static final boolean IS_ERROR = true;
	private static final String LEFT_LOG = "[";
	private static final String SPILT_TAG = ":";
	private static final String RIGTH_LOG="]";
	private static final String SPILT_MSG="=>";
	
	public static void v(String tag, String msg) {
	    if (IS_VERBOSE) {
	      tag = LEFT_LOG +Thread.currentThread().getName() + SPILT_TAG + tag +RIGTH_LOG ;
	      Log.v( TAG, tag + SPILT_MSG + msg);
	  }
	}
	
	public static void d(String tag, String msg) {
	    if (IS_DEBUG) {
	      tag = LEFT_LOG + Thread.currentThread().getName() + SPILT_TAG + tag +RIGTH_LOG;
	      Log.d( TAG, tag + SPILT_MSG + msg);
	  }
	}
	
	public static void i(String tag, String msg) {
	    if (IS_INFO) {
	      tag = LEFT_LOG + Thread.currentThread().getName() + SPILT_TAG + tag +RIGTH_LOG;
	      Log.i( TAG, tag + SPILT_MSG + msg);
	  }
	}
	
	public static void w(String tag, String msg) {
	    if (IS_WARNING) {
	      tag = LEFT_LOG + Thread.currentThread().getName() + SPILT_TAG + tag +RIGTH_LOG;
	      Log.w( TAG, tag + SPILT_MSG + msg);
	  }
	}
	
	public static void e(String tag, String msg) {
	    if (IS_ERROR) {
	      tag = LEFT_LOG + Thread.currentThread().getName() + SPILT_TAG + tag +RIGTH_LOG;
	      Log.e( TAG, tag + SPILT_MSG + msg);
	  }
	}
}
