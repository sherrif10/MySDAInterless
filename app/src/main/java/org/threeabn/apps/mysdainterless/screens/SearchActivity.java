package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;

import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.android.adapters.ProgramsList;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.util.List;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class SearchActivity extends ListActivity {

    @Override
    protected List<Program> defineInitialPrograms() {
        return filterPrograms(null, null);
    }

    @Override
    protected ProgramSearchCriteria defineProgramCategoriesSearchCriteria(ProgramCategory programCategory, String term) {
        return new ProgramSearchCriteria(programCategory.name() + ProgramsList.SEPARATOR + term, ProgramSearchCriteria.TermCategory.SEARCH, null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        hideSoftKeyboard(R.id.searchText);
    }
}
