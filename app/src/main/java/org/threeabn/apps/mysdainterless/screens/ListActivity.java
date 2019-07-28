package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;

public abstract class ListActivity extends VideoActivity {
    private ProgramsList listAdapter;
    private int categoriesInitialization = 0;
    protected abstract String[] defineInitialProgramsPaths();
    protected abstract ProgramSearchCriteria defineProgramCategoriesSearchCriteria(Program.ProgramCategory programCategory);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, Program.ProgramCategory.displayNames());

        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner categories = (Spinner) findViewById(R.id.programCategorySpinner);
        categories.setAdapter(categoriesAdapter);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Program.ProgramCategory selectedCategory = Program.ProgramCategory.valueOfFromDisplayName(parent.getItemAtPosition(position).toString());
                if(++ categoriesInitialization > 1 && selectedCategory != null && listAdapter != null) {
                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(defineProgramCategoriesSearchCriteria(selectedCategory)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        updateProgramItems(defineInitialProgramsPaths());
    }

    private void updateProgramItems(String[] programsPaths) {
        if(programsPaths != null) {
            listAdapter = new ProgramsList(ListActivity.this, programsPaths);
            ListView list = findViewById(R.id.list_programs_view);

            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String name = parent.getItemAtPosition(position).toString();
                    File selectedProgram = new File(MySDAInterlessApp.PROGRAMS_DIRECTORY + File.separator + programsPaths[position]);

                    if(selectedProgram != null && selectedProgram.exists()) {
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
