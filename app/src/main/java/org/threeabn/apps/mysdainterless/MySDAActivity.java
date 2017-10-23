package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.threeabn.apps.mysdainterless.api.MySDAService;

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
                    startActivity(new Intent(context, ListActivity.class));
                } else if(R.id.image_threeadn == v.getId()) {
                    startActivity(new Intent(context, MyChannelActivity.class));
                } else if(R.id.image_threeadn == v.getId()) {
                    startActivity(new Intent(context, MyChannelActivity.class));
                } else if(R.id.image_threeadn == v.getId()) {
                    startActivity(new Intent(context, MyChannelActivity.class));
                } else if(R.id.imageView_user == v.getId()) {
                    if(getService() != null && getService().checkIfLoggedIn()) {//TODO show menu such as; change password, edit user details etc
                        startActivity(new Intent(context, UserActivity.class));
                    } else {
                        startActivity(new Intent(context, LoginActivity.class));//TODO show menu such as; login, signup (only available for admin at first installation)
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //show title bar when not on main screen
        if(!(this instanceof MainActivity)) {
            setTheme(android.R.style.Theme_DeviceDefault_Light_DarkActionBar);
        }
        if(!(this instanceof LoginActivity) || !(this instanceof UserActivity) || !(this instanceof ProgramsActivity)) {
            //TODO maybe database setup (creation of these tables) should be loaded here and nothing else


        }
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
}
