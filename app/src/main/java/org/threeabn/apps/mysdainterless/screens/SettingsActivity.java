package org.threeabn.apps.mysdainterless.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.StatusesList;
import org.threeabn.apps.mysdainterless.settings.OrderBy;
import org.threeabn.apps.mysdainterless.settings.Repeat;
import org.threeabn.apps.mysdainterless.utils.FilesUtils;
import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SettingsActivity extends MySDAActivity {
   private TextView version;
   private TextView releaseDate;
   private TextView installationDate;
   private ListView statuses;
   private EditText previewSeconds;
   private Spinner repeat;
   private Spinner orderBy;
   private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        version = findViewById(R.id.version);
        releaseDate = findViewById(R.id.releaseDate);
        installationDate = findViewById(R.id.installationDate);
        statuses = findViewById(R.id.statuses);
        previewSeconds = findViewById(R.id.previewSeconds);
        repeat = findViewById(R.id.repeat);
        orderBy = findViewById(R.id.orderBy);
        submit = findViewById(R.id.submit);

        // render info and settings
        version.setText(settings.getVersion());

        releaseDate.setText(localeFormat.format(settings.getReleaseDate()));

        installationDate.setText(localeFormat.format(settings.getInstallationDate()));

        ListAdapter listAdapter = new StatusesList(this, settings.getStatuses());
        statuses.setAdapter(listAdapter);

        previewSeconds.setText(settings.getPreviewSeconds().toString());

        ArrayAdapter<String> repeatAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, moveItemToIndex0(Stream.of(Repeat.values()).map(Repeat::name).toArray(String[]::new), settings.getRepeat().name()));
        repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        repeat.setAdapter(repeatAdapter);
        repeat.requestFocus();

        ArrayAdapter<String> orderByAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, moveItemToIndex0(Stream.of(OrderBy.values()).map(OrderBy::name).toArray(String[]::new), settings.getOrderBy().name()));
        orderByAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderBy.setAdapter(orderByAdapter);

        // handle submission
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String secs = previewSeconds.getText().toString();
                if(StringUtils.isNotBlank(secs)) {
                    settings.setPreviewSeconds(Integer.parseInt(secs));
                    settings.setOrderBy(OrderBy.valueOf(orderBy.getSelectedItem().toString()));
                    settings.setRepeat(Repeat.valueOf(repeat.getSelectedItem().toString()));
                    try {
                        FilesUtils.write(settingsFile.getAbsolutePath(), SettingsUtils.toJSONString(settings));
                        startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    previewSeconds.requestFocus();
                }
            }
        });
    }

    private String[] moveItemToIndex0(String[] items, String item) {
        List<String> itemsList = new ArrayList<>(items.length);
        itemsList.add(item);
        for(String i : items) {
            if(!i.equals(item)) {
                itemsList.add(i);
            }
        }
        return itemsList.toArray(new String[items.length]);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
