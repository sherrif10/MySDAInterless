package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
            ListView list = (ListView) findViewById(R.id.list_programs_view);

            list.setAdapter(listAdapter);
            //list.setOnScrollListener(new ItemsScroller());
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File selectedProgram = new File(MySDAInterlessApp.getInstance().PROGRAMS_DIRECTORY + File.separator + programsPaths[position]);

                    if(selectedProgram != null && selectedProgram.exists()) {
                        findViewById(R.id.programPreviewPlay).setTag(selectedProgram.getAbsolutePath());
                        playProgram(R.id.programPreview, Uri.fromFile(selectedProgram));
                        Toast.makeText(ProgramsListActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        loadActivityByView(findViewById(R.id.programPreviewPlay), ProgramsListActivity.this);
    }


}
