package org.threeabn.apps.mysdainterless;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadActivityByView(findViewById(R.id.image_search), MainActivity.this);
        loadActivityByView(findViewById(R.id.image_favorite), MainActivity.this);
        loadActivityByView(findViewById(R.id.image_list), MainActivity.this);
        loadActivityByView(findViewById(R.id.image_threeadn), MainActivity.this);
    }
}
