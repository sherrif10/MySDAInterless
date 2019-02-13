package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class SearchResultsActivity extends VideoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        String searchText = getIntent().getExtras().get(SearchActivity.SEARCH_TEXT).toString();
        String[] foundProgramsPaths = MySDAInterlessApp.getInstance().getExistingProgramRefs();
        ProgramsList listAdapter = new ProgramsList(SearchResultsActivity.this, foundProgramsPaths, true);
        ListView list = findViewById(R.id.search_programs_list);
        TextView foundSearchResults = findViewById(R.id.foundSearchResults);

        list.setAdapter(listAdapter);
        if(foundProgramsPaths != null) {
            foundSearchResults.setText(getString(R.string.search_found_results, foundProgramsPaths.length, searchText));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File selectedProgram = new File(MySDAInterlessApp.PROGRAMS_DIRECTORY + File.separator + foundProgramsPaths[position]);

                    if (selectedProgram != null && selectedProgram.exists()) {
                        Intent intent = new Intent(getApplicationContext(), PlayBackActivity.class);

                        intent.putExtra("program", selectedProgram.getAbsolutePath());
                        startActivity(intent);
                        Toast.makeText(SearchResultsActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
