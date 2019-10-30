package org.threeabn.apps.mysdainterless.screens;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.CustomMediaController;
import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Playback;
import org.threeabn.apps.mysdainterless.settings.NextRun;
import org.threeabn.apps.mysdainterless.settings.Repeat;
import org.threeabn.apps.mysdainterless.utils.FilesUtils;
import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

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

    //TODO this should rather load the program name from the api using this current returned file name/code
    public String getFileDisplayName(String path) {
        if (StringUtils.isNotBlank(path))
            return path.substring(path.lastIndexOf(File.separator)).replace(File.separator, "");
        return null;
    }

    public void playProgram(int playerId, Playback playBack) {
        final VideoView videoView = findViewById(playerId);
        position = playBack.getPosition();
        selectedProgram = new File(MySDAInterlessApp.getInstance().getProgramsDirectory() + File.separator + playBack.getProgramRefs().get(playBack.getProgramRefs().keySet().toArray()[position]));
        if (videoView != null && selectedProgram != null && selectedProgram.exists()) {
            //todo decrypt and encrypt back when stopped
            //CryptoLauncher.dencrypt(program);
            videoView.setVideoURI(Uri.fromFile(selectedProgram));
            videoView.setMediaController(new CustomMediaController(this));

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(!isRepeatAll(playBack));
                    videoView.start();
                    //CryptoLauncher.encrypt(program);
                }
            });

            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                  if(isRepeatAll(playBack)) {
                      if(position == playBack.getProgramRefs().keySet().size() - 1) {
                          position = playBack.getPosition();
                      } else {
                          position++;
                      }
                      selectedProgram = new File(MySDAInterlessApp.getInstance().getProgramsDirectory() + File.separator + playBack.getProgramRefs().get(playBack.getProgramRefs().keySet().toArray()[position]));
                      videoView.setVideoURI(Uri.fromFile(selectedProgram));
                      videoView.start();
                  }
                }
            });
            Toast.makeText(VideoActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isRepeatAll(Playback playBack) {
        File settingsFile = new File(MySDAInterlessApp.getInstance().getSettingsFile());
        boolean isRepeatAll = false;
        try {
            isRepeatAll = Playback.Mode.FULL_SCREEN.equals(playBack.getMode()) && settingsFile != null && settingsFile.length() > 0 && Repeat.REPEAT_ALL.equals(SettingsUtils.fromJSONString(FilesUtils.read(settingsFile.getAbsolutePath())).getRepeat());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isRepeatAll;
    }
}
