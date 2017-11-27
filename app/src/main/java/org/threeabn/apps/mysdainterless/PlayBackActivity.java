package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;

import java.io.File;

/**
 * Created by k-joseph on 23/10/2017.
 */

public class PlayBackActivity extends MySDAActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        playProgram(R.id.videoView_playback, Uri.fromFile(new File(getIntent().getStringExtra("program"))));
        loadActivityByView(findViewById(R.id.button_full_sreen), PlayBackActivity.this);
    }
}
