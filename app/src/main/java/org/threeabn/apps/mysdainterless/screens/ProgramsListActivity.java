package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class ProgramsListActivity extends VideoActivity {
    private String[] programsPaths = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        programsPaths = MySDAInterlessApp.getInstance().getExistingProgramRefs();
        if(programsPaths != null) {
            ProgramsList listAdapter = new ProgramsList(ProgramsListActivity.this, programsPaths);
            ListView list = findViewById(R.id.list_programs_view);

            list.setAdapter(listAdapter);
            //list.setOnScrollListener(new ItemsScroller());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //TODO fix, use code to retrieve program not position
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
        runBlockByView(findViewById(R.id.programPreviewPlay), ProgramsListActivity.this);
        runBlockByView(findViewById(R.id.programPreviewFavorite), ProgramsListActivity.this);
    }


}
