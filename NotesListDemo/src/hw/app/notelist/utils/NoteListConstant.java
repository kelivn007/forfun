/**
 * 
 */
package hw.app.notelist.utils;

import android.os.Environment;

/**
 * @author huangwei05
 *
 */
public class NoteListConstant {
    public static final int RESULT_CAPTURE_IMAGE = 1;
    public static final int RESULT_GET_PHOTO = 2;
    public static final int RESULT_GET_BILL = 3;
    public static final int RESULT_GET_AUDIO = 4;
    public static final int RESULT_GET_VIDEO = 5;
    public static final int RESULT_UPDATE_BILL = 6;
    
	public static final String SCREEN_WIDTH = "screen_width";
	public static final String SCREEN_HEIGHT = "screen_height";
	public static final String FILE_SYSTEM_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	
	
	
	
	
	public static final String FILE_DIR_SPILT = "/";
	public static String APP_FILE_ROOT = FILE_SYSTEM_ROOT;
	public static String APP_DIARY_FILE_ROOT = APP_FILE_ROOT + FILE_DIR_SPILT + "NoteList"+FILE_DIR_SPILT + "diary" + FILE_DIR_SPILT;
	public static String APP_RECORD_FILE_ROOT = APP_FILE_ROOT + FILE_DIR_SPILT + "NoteList"+FILE_DIR_SPILT + "record" +FILE_DIR_SPILT ;
}
