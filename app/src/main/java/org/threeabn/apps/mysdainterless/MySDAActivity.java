package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class MySDAActivity extends Activity {
    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void loadActivityByView(View view, final Context context) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(R.id.image_search == v.getId()) {
                    startActivity(new Intent(context, SearchActivity.class));
                } else if(R.id.image_favorite == v.getId()) {
                    startActivity(new Intent(context, FavoriteActivity.class));
                } else if(R.id.image_list == v.getId()) {
                    startActivity(new Intent(context, ListActivity.class));
                } else if(R.id.image_threeadn == v.getId()) {
                    startActivity(new Intent(context, MyChannelActivity.class));
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftKeyboard();
    }
}
