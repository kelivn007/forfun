package hw.app.notelist.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore.Images;

public class ImageUtils {
    private static final String TAG = "NoteListUtil";

    public static Bitmap getBitmapFromPath(String path) {
        return getBitmapFromPath(path, 1000, 1000);
    }

    public static Bitmap getBitmapFromPath(String path, int maxWidth, int maxHeight) {
        try {
            Bitmap bitmap = null;
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            int imgWidth = newOpts.outWidth;
            int imgHeight = newOpts.outHeight;

            int scale = 1;
            if (imgWidth > imgHeight && imgWidth > maxWidth) {
                scale = (int) (imgWidth / maxWidth);
            } else if (imgHeight > imgWidth && imgHeight > maxHeight) {
                scale = (int) (imgHeight / maxHeight);
            }

            NoteListLog.i(TAG, "getBitmapFromPath path = " + path + " imgWidth = " + imgWidth + " imgHeight = "
                    + imgHeight + " maxWidth = " + maxWidth + " maxHeight = " + maxHeight + " scale = " + scale);

            newOpts.inSampleSize = scale;// 设置缩放比例
            bitmap = BitmapFactory.decodeFile(path, newOpts);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getBitmapFromUri(ContentResolver contentResolver, Uri uri, int maxWidth, int maxHeight) {
        try {
            Bitmap bitmap = null;
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, newOpts);
            newOpts.inJustDecodeBounds = false;
            int imgWidth = newOpts.outWidth;
            int imgHeight = newOpts.outHeight;

            int scale = 1;
            if (imgWidth > imgHeight && imgWidth > maxWidth) {
                scale = (int) (imgWidth / maxWidth);
            } else if (imgHeight > imgWidth && imgHeight > maxHeight) {
                scale = (int) (imgHeight / maxHeight);
            }

            NoteListLog.i(TAG, "getBitmapFromUri uri = " + uri + " imgWidth = " + imgWidth + " imgHeight = "
                    + imgHeight + " maxWidth = " + maxWidth + " maxHeight = " + maxHeight + " scale = " + scale);

            newOpts.inSampleSize = scale;// 设置缩放比例
            bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, newOpts);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap createVideoThumbnail(String path) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, Images.Thumbnails.MINI_KIND);
        return bitmap;
    }
}
