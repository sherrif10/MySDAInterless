package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Playback;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;
import org.threeabn.apps.mysdainterless.utils.FilesUtils;
import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class ListActivity extends VideoActivity {
    private ProgramsList listAdapter;
    private int categoriesInitialization = 0;

    protected abstract List<Program> defineInitialPrograms();

    protected abstract ProgramSearchCriteria defineProgramCategoriesSearchCriteria(ProgramCategory programCategory, String term);

    private ListActivity currentScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        currentScreen = this;

        super.onCreate(savedInstanceState);
        Spinner categories = findViewById(R.id.programCategorySpinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_spinner_item, ProgramCategory.displayNames());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoriesAdapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ProgramCategory selectedCategory = ProgramCategory.valueOfFromDisplayName(parent.getItemAtPosition(position).toString());
                if (++categoriesInitialization > 1 && selectedCategory != null && listAdapter != null && !(currentScreen instanceof SearchActivity)) {
                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory, null)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        updateProgramItems(defineInitialPrograms());

        runActivityByView(findViewById(R.id.programPreviewPlay), ListActivity.this);

        if (currentScreen instanceof ProgramsListActivity || currentScreen instanceof FavoriteActivity) {
            runActivityByView(findViewById(R.id.programPreviewFavorite), ListActivity.this);
        } else if (currentScreen instanceof SearchActivity) {
            findViewById(R.id.searchEnter).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ProgramCategory selectedCategory = ProgramCategory.valueOfFromDisplayName(categories.getSelectedItem().toString());
                    String term = ((EditText) currentScreen.findViewById(R.id.searchText)).getText().toString();

                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory, term)));
                }
            });
        }
    }

    private void updateProgramItems(List<Program> programs) {
        if (programs != null) {
            LinkedHashMap<String, String> programRefs = new LinkedHashMap<>();
            programs.stream().forEach(p -> programRefs.put(p.getDisplayName(), p.getCategory().getDisplayName() + File.separator + p.getFileName()));
            String[] programDisplays = programRefs.keySet().toArray(new String[programRefs.size()]);
            listAdapter = new ProgramsList(ListActivity.this, programDisplays, programRefs);
            ListView list = findViewById(R.id.list_programs_view);
            //list.setFriction(ViewConfiguration.getScrollFriction() * 100);

            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Playback playBack = new Playback(position, programRefs, currentScreen instanceof ProgramsListActivity ? Playback.Mode.PREVIEW_AUTO_PLAY : Playback.Mode.PREVIEW);
                    findViewById(R.id.programPreviewPlay).setTag(playBack);
                    findViewById(R.id.programPreviewFavorite).setTag(playBack);
                    playProgram(R.id.programPreview, playBack);
                    if(currentScreen instanceof ProgramsListActivity) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                findViewById(R.id.programPreviewPlay).performClick();
                            }
                        }, getPreviewSeconds(playBack) * 1000);
                    }
                }
            });
        }
    }
    private Integer getPreviewSeconds(Playback playback) {
        File settingsFile = new File(MySDAInterlessApp.getInstance().getSettingsFile());
        Integer seconds = 15;
        try {
            seconds =  settingsFile != null && settingsFile.length() > 0 ? SettingsUtils.fromJSONString(FilesUtils.read(settingsFile.getAbsolutePath())).getPreviewSeconds() : seconds;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return seconds;
    }
}
