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

public class SearchActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final File programsFolder = new File(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY);
        ProgramsList listAdapter = new ProgramsList(SearchActivity.this, filterOutNonVideoFiles(programsFolder.list()), true);
        ListView list = (ListView) findViewById(R.id.search_programs_list);

        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selectedProgram = programsFolder.listFiles()[position];

                if(selectedProgram != null && selectedProgram.exists()) {
                    findViewById(R.id.programPreviewPlay).setTag(selectedProgram.getAbsolutePath());
                    playProgram(R.id.programPreview, Uri.fromFile(selectedProgram));
                    Toast.makeText(SearchActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
