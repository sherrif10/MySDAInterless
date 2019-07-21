package org.threeabn.apps.mysdainterless.screens;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.CustomMediaController;
import org.threeabn.apps.mysdainterless.security.CryptoLauncher;

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
            return path.substring(path.lastIndexOf(File.separator)).replace(File.separator, "");
        return null;
    }

    public void playProgram(int playerId, File program) {
        final VideoView videoView = findViewById(playerId);

        //todo decrypt and encrypt back when stopped
        //CryptoLauncher.dencrypt(program);
        videoView.setVideoURI(Uri.fromFile(program));
        videoView.setMediaController(new CustomMediaController(this));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
                videoView.requestFocus();
                //CryptoLauncher.encrypt(program);
            }
        });
    }
}
