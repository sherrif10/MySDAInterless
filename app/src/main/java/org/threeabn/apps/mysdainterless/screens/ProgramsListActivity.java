package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
import java.util.Arrays;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class ProgramsListActivity extends ListActivity {
    @Override
    protected String[] defineInitialProgramsPaths() {
        return MySDAInterlessApp.getInstance().getExistingProgramRefs();
    }

    @Override
    protected ProgramSearchCriteria defineProgramCategoriesSearchCriteria(Program.ProgramCategory programCategory, String term) {
        return new ProgramSearchCriteria(programCategory, ProgramSearchCriteria.TermCategory.CATEGORY, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_list);
        super.onCreate(savedInstanceState);
    }

}
