package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.util.ArrayList;
import java.util.List;

import static org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType;

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

    /**
     * @param codes
     * @param searchText, can be null or applied one or with favorited
     * @param favorited,  can be null or applied one or with searchText
     * @return
     */
    public String[] filterPrograms(String[] codes, String searchText, Boolean favorited) {
        List<String> strs = new ArrayList<String>();

        if (codes != null) {
            for (String s : codes) {//TODO
                Program p = null;
                try {
                    p = ((MySDAInterlessApp) getApplication()).getService().getProgramByCode(s.substring(0, s.indexOf(".")));
                } catch (Exception e) {
                    Log.e("SQL_ERROR: ", e.getLocalizedMessage());
                    continue;
                }
                if (checkIfFileNameBelongsToVideoType(s) && p != null) {
                    if (TextUtils.isEmpty(searchText) || (!TextUtils.isEmpty(searchText) &&
                            ((!TextUtils.isEmpty(p.getName()) && p.getName().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getCategory()) && p.getCategory().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getSeries()) && p.getSeries().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getEpisode()) && p.getEpisode().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getCode()) && p.getCode().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getDescription()) && p.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getParticipants()) && p.getParticipants().toLowerCase().contains(searchText.toLowerCase()))
                                    || (!TextUtils.isEmpty(p.getDuration()) && p.getDuration().toLowerCase().contains(searchText.toLowerCase()))))) {

                        if (favorited == null || (favorited != null && favorited && p.isFavourited())) {
                            strs.add(s);
                        }
                    }
                }
            }

        }
        return strs.size() > 0 ? strs.toArray(new String[strs.size()]) : new String[]{};
    }

    /*
     * TODO:
     * 1. replaced list adapters with fragmentation
     * 2. increase search speed by caching programs
     */
}
