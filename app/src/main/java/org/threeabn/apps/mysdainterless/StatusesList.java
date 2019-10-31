package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import org.threeabn.apps.mysdainterless.modal.ProgramCategory;
import org.threeabn.apps.mysdainterless.settings.Status;

import java.util.List;

/**
 *
 */
public class StatusesList extends ArrayAdapter<String> implements Filterable {
    private Activity context;
    private List<Status> statuses;

    public StatusesList(Activity context, List<Status> statuses) {
        super(context, R.layout.list_statuses, statuses.stream().map(Status::getCategory).map(ProgramCategory::getDisplayName).toArray(String[]::new));
        this.context = context;
        this.statuses = statuses;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_statuses, null, true);
        TextView category = rowView.findViewById(R.id.category);
        TextView count = rowView.findViewById(R.id.count);
        category.setGravity(Gravity.LEFT|Gravity.END|Gravity.CENTER_VERTICAL);
        category.setText(statuses.get(position).getCategory().getDisplayName() + " : ");
        count.setText(statuses.get(position).getCount().toString());
        count.setGravity(Gravity.RIGHT|Gravity.END|Gravity.CENTER_VERTICAL);

        return rowView;
    }

}
