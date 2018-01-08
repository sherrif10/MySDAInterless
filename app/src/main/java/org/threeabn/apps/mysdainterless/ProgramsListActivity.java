package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class ProgramsListActivity extends MySDAActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final File programsFolder = new File(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY);

        if(programsFolder != null && programsFolder.exists() && programsFolder.list().length> 0) {
            ProgramsList listAdapter = new ProgramsList(ProgramsListActivity.this, filterOutNonVideoFiles(programsFolder.list()));
            ListView list = (ListView) findViewById(R.id.list_programs_view);
            EditText inputSearch = (EditText) findViewById(R.id.searchText);

            if(inputSearch != null) {
                /**
                 * Enabling Search Filter
                 * */
                inputSearch.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        listAdapter.getFilter().filter(cs);
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable arg0) {
                    }
                });
            }
            list.setAdapter(listAdapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    File selectedProgram = programsFolder.listFiles()[position];

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
