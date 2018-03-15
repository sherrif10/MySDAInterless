package org.threeabn.apps.mysdainterless;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.video.CustomMediaController;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class VideoActivity extends MySDAActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //TODO this should rather load the program name from the api using this current returned file name/code
    public String getFileDisplayName(String path) {
        if (StringUtils.isNotBlank(path))
            return path.substring(path.lastIndexOf(File.separator), path.length()).replace(File.separator, "");
        return null;
    }

    public void playProgram(int playerId, Uri program) {
        final VideoView videoView = (VideoView) findViewById(playerId);

        videoView.setVideoURI(program);
        videoView.setMediaController(new MediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
                videoView.requestFocus();
            }
        });
    }
}
