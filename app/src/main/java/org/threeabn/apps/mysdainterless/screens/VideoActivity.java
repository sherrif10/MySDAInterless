package org.threeabn.apps.mysdainterless.screens;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import org.threeabn.apps.mysdainterless.CustomMediaController;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Playback;
import org.threeabn.apps.mysdainterless.settings.Repeat;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class VideoActivity extends MySDAActivity {
    private int position;
    private File selectedProgram;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void playProgram(int playerId, Playback playBack) {
        final VideoView videoView = findViewById(playerId);
        position = playBack.getPosition();
        selectedProgram = new File(getProgramsDirectory().getAbsolutePath() + File.separator + playBack.getProgramRefs().get(playBack.getProgramRefs().keySet().toArray()[position]));
        if (videoView != null && selectedProgram != null && selectedProgram.exists()) {
            videoView.setVideoURI(Uri.fromFile(selectedProgram));
            videoView.setMediaController(new CustomMediaController(this));

            videoView.setOnPreparedListener((MediaPlayer mp) -> {
                mp.setLooping(!isRepeatAll(playBack));
                videoView.start();
                Toast.makeText(VideoActivity.this, getString(R.string.playing) + getFileNameWithOutExtension(selectedProgram), Toast.LENGTH_SHORT).show();
            });

            videoView.setOnCompletionListener((MediaPlayer mp) -> {
                if (isRepeatAll(playBack)) {
                    if (position == playBack.getProgramRefs().keySet().size() - 1) {
                        position = playBack.getPosition();
                    } else {
                        position++;
                    }
                    selectedProgram = new File(getProgramsDirectory().getAbsolutePath() + File.separator + playBack.getProgramRefs().get(playBack.getProgramRefs().keySet().toArray()[position]));
                    videoView.setVideoURI(Uri.fromFile(selectedProgram));
                    videoView.start();
                    Toast.makeText(VideoActivity.this, getString(R.string.playing) + getFileNameWithOutExtension(selectedProgram), Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private boolean isRepeatAll(Playback playBack) {
        return Playback.Mode.FULL_SCREEN.equals(playBack.getMode()) && settingsFile != null && settingsFile.length() > 0 && Repeat.REPEAT_ALL.equals(settings.getRepeat());
    }
}
