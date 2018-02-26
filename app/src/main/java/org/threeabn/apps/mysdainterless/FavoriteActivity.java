package org.threeabn.apps.mysdainterless;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class FavoriteActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        final File programsFolder = new File(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY);
        String[] foundProgramsPaths = filterOutNonVideoFilesAndMatchSearchPhraseOrFavorited(programsFolder.list(), null, true);
        ProgramsList listAdapter = new ProgramsList(FavoriteActivity.this, foundProgramsPaths, true);
        ListView list = (ListView) findViewById(R.id.favorite_programs_list);

        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File selectedProgram = programsFolder.listFiles()[position];

                if(selectedProgram != null && selectedProgram.exists()) {
                    Intent intent = new Intent(getApplicationContext(), PlayBackActivity.class);

                    intent.putExtra("program", selectedProgram.getAbsolutePath());
                    startActivity(intent);
                    Toast.makeText(FavoriteActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
