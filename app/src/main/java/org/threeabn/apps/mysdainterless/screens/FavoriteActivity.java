package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;

import org.threeabn.apps.mysdainterless.MySDAInterlessApp;
import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.util.List;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class FavoriteActivity extends ListActivity {

    @Override
    protected List<Program> defineInitialPrograms() {
        return MySDAInterlessApp.getInstance().filterPrograms(null, true);
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
