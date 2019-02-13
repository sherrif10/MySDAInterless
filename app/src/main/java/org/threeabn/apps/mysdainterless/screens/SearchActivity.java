package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.threeabn.apps.mysdainterless.R;

/**
 * Created by k-joseph on 10/10/2017.
 */

public class SearchActivity extends MySDAActivity {
    public static String SEARCH_TEXT = "searchText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        EditText inputSearch = findViewById(R.id.searchText);

        inputSearch.requestFocus();
        showSoftKeyboard(this, inputSearch);
        findViewById(R.id.searchEnter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(inputSearch.getText()) && inputSearch.getText().length() >= 3) {
                    Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
                    i.putExtra(SEARCH_TEXT, inputSearch.getText());
                    getApplicationContext().startActivity(i);
                }
            }
        });
    }
}
