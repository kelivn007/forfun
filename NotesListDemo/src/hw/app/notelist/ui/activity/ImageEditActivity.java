/**
 * 
 */
package hw.app.notelist.ui.activity;

import hw.app.notelist.R;
import hw.app.notelist.ui.BaseActivity;
import hw.app.notelist.ui.BasePresenter;
import hw.app.notelist.utils.ImageUtils;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author huangwei05
 *
 */
public class ImageEditActivity extends BaseActivity {
	public static final String TAG = "ImageActivity";
	
	public static final String IMAGE_PATH = "image_path";
	
	private ImageView mImageView;
	private String mImagePath;
	
	/* (non-Javadoc)
	 * @see com.baidu.xcloud.cloudnote.ui.BaseActivity#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return TAG;
	}

	/* (non-Javadoc)
	 * @see com.baidu.xcloud.cloudnote.ui.BaseActivity#getLayoutId()
	 */
	@Override
	protected int getLayoutId() {
		// TODO Auto-generated method stub
		return R.layout.image_edit_activity;
	}

	/* (non-Javadoc)
	 * @see com.baidu.xcloud.cloudnote.ui.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageView = (ImageView) findViewById(R.id.image);
		
		mImagePath = getIntent().getStringExtra(IMAGE_PATH);
		if (mImagePath != null) {
			Bitmap bitmap = ImageUtils.getBitmapFromPath(mImagePath, 2000, 2000);
			mImageView.setImageBitmap(bitmap);
		}else {
			mImageView.setImageResource(R.drawable.failed_preview_pic);
		}
	}

	/* (non-Javadoc)
	 * @see com.baidu.xcloud.cloudnote.ui.BaseActivity#initLinstener()
	 */
	@Override
	protected void initLinstener() {
		// TODO Auto-generated method stub

	}

    @Override
    public BasePresenter getPresenter() {
        // TODO Auto-generated method stub
        return null;
    }

}
