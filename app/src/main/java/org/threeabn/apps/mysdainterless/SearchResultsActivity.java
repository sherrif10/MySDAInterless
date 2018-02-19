package org.threeabn.apps.mysdainterless;

import android.content.Intent;
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

public class SearchResultsActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        String searchText = getIntent().getExtras().get(SearchActivity.SEARCH_TEXT).toString();
        final File programsFolder = new File(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY);
        ProgramsList listAdapter = new ProgramsList(SearchResultsActivity.this, filterOutNonVideoFilesAndMatchSearchPhrase(programsFolder.list(), searchText), true);
        ListView list = (ListView) findViewById(R.id.search_programs_list);

        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selectedProgram = programsFolder.listFiles()[position];

                if(selectedProgram != null && selectedProgram.exists()) {
                    Intent intent = new Intent(getApplicationContext(), PlayBackActivity.class);

                    intent.putExtra("program", selectedProgram.getAbsolutePath());
                    startActivity(intent);
                    Toast.makeText(SearchResultsActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
