package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class MySDAActivity extends Activity {

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showSoftKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public void loadActivityByView(final View view, final Context context) {
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (R.id.image_search == v.getId()) {
                    startActivity(new Intent(context, SearchActivity.class));
                } else if (R.id.image_favorite == v.getId()) {
                    startActivity(new Intent(context, FavoriteActivity.class));
                } else if (R.id.image_list == v.getId()) {
                    startActivity(new Intent(context, ProgramsListActivity.class));
                } else if (R.id.programPreviewPlay == v.getId() && StringUtils.isNoneBlank((String) view.getTag())) {
                    Intent intent = new Intent(context, PlayBackActivity.class);

                    intent.putExtra("program", (String) view.getTag());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO restrict portrait after testing on the dongo
        //hideSoftKeyboard();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
