package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.R;

public class MainActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Move to MySDAInterlessApp#oncreate
        MySDAInterlessApp.getInstance().initialiseAllPrograms();

        runActivityByView(findViewById(R.id.image_search), MainActivity.this);
        runActivityByView(findViewById(R.id.image_favorite), MainActivity.this);
        runActivityByView(findViewById(R.id.image_list), MainActivity.this);
    }
}
