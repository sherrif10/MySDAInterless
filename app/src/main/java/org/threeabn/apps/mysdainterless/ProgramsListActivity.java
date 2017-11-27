package org.threeabn.apps.mysdainterless;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType;

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

    public String[] filterOutNonVideoFiles(String[] names) {
        List<String> strs = new ArrayList<String>();

        if(names != null) {
            for(String s : names) {//TODO
                if(checkIfFileNameBelongsToVideoType(s)) {
                    strs.add(s);
                }
            }
        }

        return strs.size() > 0 ? strs.toArray(new String[strs.size()]) : new String[]{};
    }
}
