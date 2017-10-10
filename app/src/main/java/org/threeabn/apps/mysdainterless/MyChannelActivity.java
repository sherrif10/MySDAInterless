package org.threeabn.apps.mysdainterless;

import android.os.Bundle;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class MyChannelActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_DeviceDefault_Light_DarkActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychannel);
    }
}
