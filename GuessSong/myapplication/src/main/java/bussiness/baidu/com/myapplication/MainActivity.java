package bussiness.baidu.com.myapplication;

import android.Manifest;
import android.media.audiofx.Visualizer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[] {Manifest.permission.MODIFY_AUDIO_SETTINGS, Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 0);
        } else {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }

    public void init() {
        Visualizer mVisualizer = new Visualizer(0);

        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {
                Log.i("ttttt", "tttt" + bytes);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {

            }
        }, Visualizer.getMaxCaptureRate(), true, false);

        mVisualizer.setCaptureSize(256);
        mVisualizer.setEnabled(true);
    }
}
