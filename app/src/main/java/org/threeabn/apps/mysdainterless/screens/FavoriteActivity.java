package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
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

public class FavoriteActivity extends VideoActivity {
    String[] foundProgramsPaths = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        foundProgramsPaths = MySDAInterlessApp.getInstance().filterPrograms(new File(MySDAInterlessApp.PROGRAMS_DIRECTORY).list(), null, true);
        ProgramsList listAdapter = new ProgramsList(FavoriteActivity.this, foundProgramsPaths, true);
        ListView list = findViewById(R.id.favorite_programs_list);

        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(foundProgramsPaths != null) {
                    File selectedProgram = new File(MySDAInterlessApp.PROGRAMS_DIRECTORY + File.separator + foundProgramsPaths[position]);

                    if (selectedProgram != null && selectedProgram.exists()) {
                        Intent intent = new Intent(getApplicationContext(), PlayBackActivity.class);

                        intent.putExtra("program", selectedProgram.getAbsolutePath());
                        startActivity(intent);
                        Toast.makeText(FavoriteActivity.this, "Opening: " + getFileDisplayName(selectedProgram.getPath()), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
