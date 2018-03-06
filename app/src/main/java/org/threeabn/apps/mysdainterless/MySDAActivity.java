package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.VideoView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
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

    public void showSoftKeyboard(Context context, EditText editText){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
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

    //TODO this should rather load the program name from the api using this current returned file name/code
    public String getFileDisplayName(String path) {
        if (StringUtils.isNotBlank(path))
            return path.substring(path.lastIndexOf(File.separator), path.length()).replace(File.separator, "");
        return null;
    }

    public void playProgram(int playerId, Uri program) {
        final VideoView videoView = (VideoView) findViewById(playerId);

        videoView.setVideoURI(program);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                videoView.start();
                videoView.requestFocus();
            }
        });
    }

    public String[] filterOutNonVideoFilesAndMatchSearchPhraseOrFavorited(String[] codes, String searchText, Boolean favorited) {
        List<String> strs = new ArrayList<String>();
        try {
            if (codes != null && !TextUtils.isEmpty(searchText)) {

                    for (String s : codes) {//TODO
                        Program p = ((MySDAInterlessApp)getApplication()).getService().getProgramByCode(s.substring(0, s.indexOf(".")));

                        if (p != null && checkIfFileNameBelongsToVideoType(s) &&
                                ((!TextUtils.isEmpty(p.getName()) && p.getName().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getCategory()) && p.getCategory().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getSeries()) && p.getSeries().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getEpisode()) && p.getEpisode().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getCode()) && p.getCode().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getDescription()) && p.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getParticipants()) && p.getParticipants().toLowerCase().contains(searchText.toLowerCase()))
                                || (!TextUtils.isEmpty(p.getDuration()) && p.getDuration().toLowerCase().contains(searchText.toLowerCase())))) {
                            strs.add(s);
                        }
                    }

            } else if(codes != null && favorited) {
                for (String s : codes) {
                    Program p = ((MySDAInterlessApp)getApplication()).getService().getProgramByCode(s.substring(0, s.indexOf(".")));

                    if(p != null && p.isFavourited()) {
                        strs.add(s);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("SQL_ERROR: ", e.getLocalizedMessage());
        }
        return strs.size() > 0 ? strs.toArray(new String[strs.size()]) : new String[]{};
    }

    /*
     * TODO:
     * 1. replaced list adapters with fragmentation
     * 2. increase search speed by caching programs
     */
}
