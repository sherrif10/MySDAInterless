package org.threeabn.apps.mysdainterless.video;

import android.app.Activity;
import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.MediaController;

/**
 * Created by k-joseph on 15/03/2018.
 */
public class CustomMediaController extends MediaController {

    public CustomMediaController(Context context, Activity activity) {
        super(new ContextThemeWrapper(context, null/*R.style.VideoPlayerTheme*/));
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        addFullScreen();
    }

    // use resources R.mipmap.ic_fullscreen and R.mipmap.ic_fullscreen_exit if needed,
    // see: http://www.zoftino.com/android-videoview-playing-videos-full-screen
    private View addFullScreen() {
        return null;
    }
}
