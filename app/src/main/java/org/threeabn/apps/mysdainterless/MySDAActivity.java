package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.api.MySDAService;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class MySDAActivity extends Activity {

    private MySDAService service;

    /**
     * DB initialised by default, just call this service
     * @return
     */
    public MySDAService getService() {
        if(service == null)
            return new MySDAService(this);
        return service;
    }
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
                    startActivity(new Intent(context, ProgramsListActivity.class));
                }/* else if(R.id.image_playback == v.getId()) {
                    startActivity(new Intent(context, PlayBackActivity.class));
                }*/
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideSoftKeyboard();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public String getFileDisplayName(String path) {
        if(StringUtils.isNotBlank(path))
            //TODO this should rather load the program name from the api using this current returned file name/code
            return path.substring(path.lastIndexOf(File.separator), path.length());
        return null;
    }
}
