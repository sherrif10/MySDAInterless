package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.ProgramsList;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class SearchActivity extends ListActivity {

    @Override
    protected String[] defineInitialProgramsPaths() {
        return MySDAInterlessApp.getInstance().getExistingProgramRefs();
    }

    @Override
    protected ProgramSearchCriteria defineProgramCategoriesSearchCriteria(Program.ProgramCategory programCategory, String term) {
        return new ProgramSearchCriteria(programCategory.name() + ProgramsList.SEPARATOR + term, ProgramSearchCriteria.TermCategory.SEARCH, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
    }
}
