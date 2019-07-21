package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
import java.util.Arrays;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class ProgramsListActivity extends VideoActivity {
    private ProgramsList listAdapter;
    private int categoriesInitialization = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
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
                    updateProgramItems(((ProgramsList.ProgramFilter) listAdapter.getFilter()).customFilter(categoriesInitialization + ";" + "category;"+ selectedCategory.name()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        String[] programsPaths = MySDAInterlessApp.getInstance().getExistingProgramRefs();
        updateProgramItems(programsPaths);
        runActivityByView(findViewById(R.id.programPreviewPlay), ProgramsListActivity.this);
        runActivityByView(findViewById(R.id.programPreviewFavorite), ProgramsListActivity.this);
    }

    private void updateProgramItems(String[] programsPaths) {
        if(programsPaths != null) {
            listAdapter = new ProgramsList(ProgramsListActivity.this, programsPaths);
            ListView list = findViewById(R.id.list_programs_view);

            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO fix, use code to retrieve program not position
                    String name = parent.getItemAtPosition(position).toString();
                    File selectedProgram = new File(MySDAInterlessApp.PROGRAMS_DIRECTORY + File.separator + programsPaths[position]);

                    if(selectedProgram != null && selectedProgram.exists()) {
                        findViewById(R.id.programPreviewPlay).setTag(selectedProgram.getAbsolutePath());
                        findViewById(R.id.programPreviewFavorite).setTag(selectedProgram.getAbsolutePath());
                        playProgram(R.id.programPreview, selectedProgram);
                        Toast.makeText(ProgramsListActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
