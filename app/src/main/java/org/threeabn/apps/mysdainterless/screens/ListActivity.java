package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class ListActivity extends VideoActivity {
    private ProgramsList listAdapter;
    private int categoriesInitialization = 0;
    protected abstract List<Program> defineInitialPrograms();
    protected abstract ProgramSearchCriteria defineProgramCategoriesSearchCriteria(Program.ProgramCategory programCategory, String term);
    private ListActivity currentScreen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        currentScreen = this;

        super.onCreate(savedInstanceState);
        Spinner categories = (Spinner) findViewById(R.id.programCategorySpinner);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(
                ListActivity.this, android.R.layout.simple_spinner_item, Program.ProgramCategory.displayNames());
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.setAdapter(categoriesAdapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Program.ProgramCategory selectedCategory = Program.ProgramCategory.valueOfFromDisplayName(parent.getItemAtPosition(position).toString());
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

        if(currentScreen instanceof ProgramsListActivity || currentScreen instanceof FavoriteActivity ) {
            runActivityByView(findViewById(R.id.programPreviewFavorite), ListActivity.this);
        } else if(currentScreen instanceof SearchActivity) {
            findViewById(R.id.searchEnter).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Program.ProgramCategory selectedCategory = Program.ProgramCategory.valueOfFromDisplayName(categories.getSelectedItem().toString());
                    String term = ((EditText) currentScreen.findViewById(R.id.searchText)).getText().toString();

                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory, term)));
                }
            });
        }
    }

    private void updateProgramItems(List<Program> programs) {
        if(programs != null) {
            Map<String, String> programRefs = new HashMap<>();
            programs.stream().forEach(p -> programRefs.put(p.getDisplayName(), p.getCategory().getDisplayName() + File.separator + p.getFileName()));
            String[] programDisplays = programRefs.keySet().toArray(new String[programRefs.size()]);
            listAdapter = new ProgramsList(ListActivity.this, programDisplays, programRefs);
            ListView list = findViewById(R.id.list_programs_view);
            //list.setFriction(ViewConfiguration.getScrollFriction() * 100);

            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File selectedProgram = new File(MySDAInterlessApp.getInstance().getProgramsDirectory() + File.separator + programRefs.get(parent.getItemAtPosition(position)));

                    if(selectedProgram != null && selectedProgram.exists() && (currentScreen instanceof ProgramsListActivity || currentScreen instanceof FavoriteActivity)) {
                        findViewById(R.id.programPreviewPlay).setTag(selectedProgram.getAbsolutePath());
                        findViewById(R.id.programPreviewFavorite).setTag(selectedProgram.getAbsolutePath());
                        playProgram(R.id.programPreview, selectedProgram);
                        Toast.makeText(ListActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
