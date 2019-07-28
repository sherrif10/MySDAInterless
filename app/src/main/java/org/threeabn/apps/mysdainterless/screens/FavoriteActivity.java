package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class FavoriteActivity extends ListActivity {

    @Override
    protected String[] defineInitialProgramsPaths() {
        return MySDAInterlessApp.getInstance().filterPrograms(new File(MySDAInterlessApp.PROGRAMS_DIRECTORY).list(), null, true);
    }

    @Override
    protected ProgramSearchCriteria defineProgramCategoriesSearchCriteria(Program.ProgramCategory programCategory, String term) {
        return new ProgramSearchCriteria(programCategory, ProgramSearchCriteria.TermCategory.CATEGORY_FAVOURITE, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_favorite);
        super.onCreate(savedInstanceState);
    }
}
