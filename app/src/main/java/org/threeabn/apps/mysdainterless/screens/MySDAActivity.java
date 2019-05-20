package org.threeabn.apps.mysdainterless.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;

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

    public void runBlockByView(final View view, final Context context) {
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
                } else if(R.id.programPreviewFavorite == v.getId() && StringUtils.isNoneBlank((String) view.getTag())) {
                    File p = new File((String) view.getTag());

                    if(p != null && p.exists()) {
                        try {
                            String s = p.getName();
                            Program program = TextUtils.isEmpty(s) ? null : MySDAInterlessApp.getInstance().getService().getProgramByCode(s.substring(0, s.indexOf(".")));
                            if(program != null) {
                                program.setFavourited(!program.isFavourited());
                                MySDAInterlessApp.getInstance().getService().saveProgram(program);
                                Toast.makeText(MySDAActivity.this, (program.isFavourited() ? "" : "Un-") + "Favorited: " + program.getName(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("program_favoriting_error", e.getMessage());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO restrict auto-rotate after testing on the dongo
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
