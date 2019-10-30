package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Playback;

import java.io.File;

/**
 * Created by k-joseph on 23/10/2017.
 */
public class PlayBackActivity extends VideoActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);
        Playback playback = (Playback) getIntent().getSerializableExtra("program");
        playback.setMode(Playback.Mode.FULL_SCREEN);
        playProgram(R.id.videoView_playback, playback);
    }
}
