package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.android.adapters.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Playback;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class ListActivity extends VideoActivity {
    private ProgramsList listAdapter;
    private int categoriesInitialization = 0;

    protected abstract List<Program> defineInitialPrograms();

    protected abstract ProgramSearchCriteria defineProgramCategoriesSearchCriteria(ProgramCategory programCategory, String term);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Spinner categories = findViewById(R.id.programCategorySpinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_spinner_item, ProgramCategory.displayNames());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoriesAdapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                ProgramCategory selectedCategory = ProgramCategory.valueOfFromDisplayName(parent.getItemAtPosition(position).toString());
                if (++categoriesInitialization > 1 && selectedCategory != null && listAdapter != null && !(currentScreen instanceof SearchActivity)) {
                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory, null)));
                }
                progressBar.setVisibility(View.GONE);
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
            findViewById(R.id.searchEnter).setOnClickListener((View v) -> {
                progressBar.setVisibility(View.VISIBLE);
                ProgramCategory selectedCategory = ProgramCategory.valueOfFromDisplayName(categories.getSelectedItem().toString());
                String term = ((EditText) currentScreen.findViewById(R.id.searchText)).getText().toString();
                updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory, term)));
                progressBar.setVisibility(View.GONE);
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
                    // only support autoplay on playback screen
                    if(currentScreen instanceof ProgramsListActivity) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(currentScreen instanceof ProgramsListActivity) {
                                    findViewById(R.id.programPreviewPlay).performClick();
                                }
                            }
                        }, settings.getPreviewSeconds() * 1000);
                    }
                }
            });
        }
    }
}
