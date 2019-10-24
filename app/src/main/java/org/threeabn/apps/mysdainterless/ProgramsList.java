package org.threeabn.apps.mysdainterless;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by k-joseph on 29/10/2017.
 */
public class ProgramsList extends ArrayAdapter<String> implements Filterable {
    private Activity context;
    public static String SEPARATOR = "<-:->";
    private Map<String, String> programRefs;

    public ProgramsList(Activity context, String[] programDisplays, Map<String, String> programRefs) {
        super(context, R.layout.list_programs, programDisplays);//TODO fetch and rather pass in program name here instead of programPaths
        this.context = context;
        this.programRefs = programRefs;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_programs, null, true);
        TextView txtTitle = rowView.findViewById(R.id.txt);
        ImageView imageView = rowView.findViewById(R.id.img);

        if (position + 1 > programRefs.size()) {
            // remove(getItem(position));
        } else {
            // TODO fix to by name not position
            String display = (String) new ArrayList(programRefs.keySet()).get(position);
            if (StringUtils.isNotBlank(display)) {
                txtTitle.setText(display);
                Bitmap img = retrieveBitmap(MySDAInterlessApp.getInstance().getProgramsDirectory() + File.separator + programRefs.get(display));
                imageView.setImageBitmap(img);
            }
        }
        return rowView;
    }

    private Bitmap retrieveBitmap(String videoPath) {
        return ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);
    }

    @Override
    public Filter getFilter() {
        return new ProgramFilter(context);
    }

    public class ProgramFilter extends Filter {

        ProgramFilter(Context context) {
            context = context;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            return null;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        }

        /**
         * I use this instead of the default filter that fails to update the list appropriately and the quick fix being rebuilding the list
         */
        public List<Program> customFilter(ProgramSearchCriteria criteria) {
            return getFilteredResults(criteria);
        }

        private List<Program> getFilteredResults(ProgramSearchCriteria criteria) {
            ArrayList<Program> listResult = new ArrayList<Program>();
            try {
                List<Program> programs = MySDAInterlessApp.getInstance().getService().getAllPrograms();
                List<Program> programsByCategories;
                List<Program> favouritedPrograms;
                List<Program> searchedPrograms;

                if (ProgramSearchCriteria.TermCategory.CATEGORY.equals(criteria.getCategory())) {//wrong constraint structure
                    programsByCategories = MySDAInterlessApp.getInstance().getService().getProgramsByCategories(Arrays.asList((ProgramCategory) criteria.getTerm()));
                    if (ProgramCategory.ALL.equals(criteria.getTerm())) {
                        listResult.addAll(programs);
                    } else {
                        listResult.addAll(programsByCategories);
                    }
                } else if (ProgramSearchCriteria.TermCategory.CATEGORY_FAVOURITE.equals(criteria.getCategory())) {
                    programsByCategories = MySDAInterlessApp.getInstance().getService().getProgramsByCategories(Arrays.asList((ProgramCategory) criteria.getTerm()));
                    favouritedPrograms = MySDAInterlessApp.getInstance().getService().getFavouritedPrograms();
                    if (ProgramCategory.ALL.equals(criteria.getTerm())) {
                        listResult.addAll(intersectTwoProgramLists(programs, favouritedPrograms));
                    } else {
                        listResult.addAll(intersectTwoProgramLists(programsByCategories, favouritedPrograms));
                    }
                } else if (ProgramSearchCriteria.TermCategory.FAVOURITE.equals(criteria.getCategory())) {
                    favouritedPrograms = MySDAInterlessApp.getInstance().getService().getFavouritedPrograms();
                    listResult.addAll(favouritedPrograms);
                } else if (ProgramSearchCriteria.TermCategory.SEARCH.equals(criteria.getCategory())) {
                    String[] terms = ((String) criteria.getTerm()).split(SEPARATOR);
                    ProgramCategory selectedCategory = ProgramCategory.valueOf(terms[0]);
                    searchedPrograms = searchPrograms(terms[1], programs);
                    if (ProgramCategory.ALL.equals(selectedCategory)) {
                        listResult.addAll(intersectTwoProgramLists(searchedPrograms, new ArrayList<>(programs)));
                    } else {
                        programsByCategories = MySDAInterlessApp.getInstance().getService().getProgramsByCategories(Arrays.asList(selectedCategory));
                        listResult.addAll(intersectTwoProgramLists(searchedPrograms, programsByCategories));
                    }
                } else {
                    listResult.addAll(searchPrograms((String) criteria.getTerm(), programs));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listResult;
        }
    }

    private List<Program> searchPrograms(String term, Collection<Program> programList) {
        List<Program> searchedPrograms = new ArrayList<>();
        for (Program p : programList) {
            if (MySDAInterlessUtils.programsMatcher(p, term)) {
                searchedPrograms.add(p);
            }
        }
        return searchedPrograms;
    }

    // get all in programs1 and are in programs2
    private List<Program> intersectTwoProgramLists(List<Program> programs1, List<Program> programs2) {
        List<Program> intersected = new ArrayList<>(programs1);
        intersected.retainAll(programs2);
        return intersected;
    }
}
