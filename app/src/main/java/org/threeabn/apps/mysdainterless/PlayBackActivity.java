package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;

/**
 * Created by k-joseph on 23/10/2017.
 */
public class PlayBackActivity extends VideoActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        playProgram(R.id.videoView_playback, Uri.fromFile(new File(getIntent().getStringExtra("program"))));
        Toast.makeText(PlayBackActivity.this, "Playing: " + getFileDisplayName(getIntent().getStringExtra("program")), Toast.LENGTH_SHORT).show();
    }
}
