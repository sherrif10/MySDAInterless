package org.threeabn.apps.mysdainterless.screens;

import android.os.Bundle;

import org.threeabn.apps.mysdainterless.ProgramSearchCriteria;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.util.List;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class FavoriteActivity extends ListActivity {

    @Override
    protected List<Program> defineInitialPrograms() {
        return filterPrograms(null, true);
    }

    @Override
    protected ProgramSearchCriteria defineProgramCategoriesSearchCriteria(ProgramCategory programCategory, String term) {
        return new ProgramSearchCriteria(programCategory, ProgramSearchCriteria.TermCategory.CATEGORY_FAVOURITE, true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_favorite);
        super.onCreate(savedInstanceState);
        afterLayoutInitialisation();
    }
}
