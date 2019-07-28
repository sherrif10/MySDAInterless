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

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by k-joseph on 29/10/2017.
 */
public class ProgramsList extends ArrayAdapter<String> implements Filterable {
    private Activity context;
    // TODO fix track programs by name not position
    private String[] programPaths;
    private boolean detailPrograms;


    public ProgramsList(Activity context, String[] programPaths) {
        super(context, R.layout.list_programs, programPaths);// TODO fetch and rather pass in program name here instead of programPaths
        this.context = context;
        this.programPaths = programPaths;
    }

    public ProgramsList(Activity context, String[] programPaths, boolean detailPrograms) {
        super(context, detailPrograms ? R.layout.list_programs_details : R.layout.list_programs, programPaths);//TODO fetch and rather pass in program name here instead of programPaths
        this.context = context;
        this.programPaths = programPaths;
        this.detailPrograms = detailPrograms;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(this.detailPrograms ? R.layout.list_programs_details : R.layout.list_programs, null, true);
        TextView txtTitle = rowView.findViewById(R.id.txt);
        ImageView imageView = rowView.findViewById(R.id.img);
        if(position + 1 > programPaths.length) {
            // remove(getItem(position));
        } else {
            String path = programPaths[position];
            Bitmap img = retrieveBitmap(MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY + File.separator + path);
            // TODO fix to by name not position
            if (StringUtils.isNotBlank(path)) {
                txtTitle.setText(path);
                imageView.setImageBitmap(img);

                if (this.detailPrograms) {
                    //TODO add all details
                    try {
                        TextView desc = rowView.findViewById(R.id.p_desc);
                        TextView dur = rowView.findViewById(R.id.p_duration);
                        TextView parts = rowView.findViewById(R.id.p_participants);
                        Program program = MySDAInterlessApp.getInstance().getService().getProgramByCode(path.substring(0, path.indexOf(".")));
                        if (program != null) {
                            desc.setText(program.getDescription());
                            dur.setText(program.getDuration());
                            parts.setText(program.getParticipants());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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
        public String[] customFilter(ProgramSearchCriteria criteria) {
            List<Program> filteredResult = getFilteredResults(criteria);
            return MySDAInterlessApp.getInstance().getExistingProgramRefsFromPrograms((List<Program>)filteredResult, null, null);
        }

        private List<Program> getFilteredResults(ProgramSearchCriteria criteria) {
            ArrayList<Program> listResult = new ArrayList<Program>();
            try {
                Map<String, Program> programs = MySDAInterlessApp.getInstance().getProgramSetByCodeFromList();
                List<Program> programsByCategories = MySDAInterlessApp.getInstance().getService().getProgramsByCategories(Arrays.asList((Program.ProgramCategory) criteria.getTerm()));
                List<Program> favouritedPrograms = MySDAInterlessApp.getInstance().getService().getFavouritedPrograms();

                if(ProgramSearchCriteria.TermCategory.CATEGORY.equals(criteria.getCategory())) {//wrong constraint structure
                    if(Program.ProgramCategory.ALL.equals(criteria.getTerm())) {
                        listResult.addAll(programs.values());
                    } else {
                        listResult.addAll(programsByCategories);
                    }
                } else if(ProgramSearchCriteria.TermCategory.CATEGORY_FAVOURITE.equals(criteria.getCategory())) {// TODO fix
                    if(Program.ProgramCategory.ALL.equals(criteria.getTerm())) {
                        listResult.addAll(intersectTwoProgramLists(new ArrayList(programs.values()), favouritedPrograms));
                    } else {
                        listResult.addAll(intersectTwoProgramLists(programsByCategories, favouritedPrograms));
                    }
                } else if(ProgramSearchCriteria.TermCategory.FAVOURITE.equals(criteria.getCategory())) {
                    listResult.addAll(favouritedPrograms);
                }  else {
                    for (Program p : programs.values()) {
                        if (MySDAInterlessUtils.programsMatcher(p, (String) criteria.getTerm())) {
                            listResult.add(p);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listResult;
        }
    }

    // get all in programs1 and are in programs2
    private List<Program> intersectTwoProgramLists(List<Program> programs1, List<Program> programs2) {
        programs1.retainAll(programs2);
        return programs1;
    }
}
