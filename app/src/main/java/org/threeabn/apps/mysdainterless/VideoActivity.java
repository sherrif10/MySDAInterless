package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
/**
 * Created by k-joseph on 10/10/2017.
 */
public class VideoActivity extends MySDAActivity {
    SimpleExoPlayer exoPlayer;

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
        stopPlayer();
        
        SimpleExoPlayerView simpleExoPlayerView = (SimpleExoPlayerView) findViewById(playerId);
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        MediaSource mediaSource = buildMediaSource(program);

        simpleExoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
    }

    /*
     * https://google.github.io/ExoPlayer/supported-formats.html
     */
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultDataSourceFactory(this,
                        Util.getUserAgent(this, "MySDAInterless")),
                new DefaultExtractorsFactory(), null, null);
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        stopPlayer();
    }

    private void stopPlayer() {
        if(exoPlayer != null) {
            exoPlayer.stop();
        }
    }
}
