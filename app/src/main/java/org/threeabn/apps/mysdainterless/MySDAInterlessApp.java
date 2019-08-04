package org.threeabn.apps.mysdainterless;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.api.MySDAService;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType;

/**
 * Created by k-joseph on 06/03/2018.
 */

public class MySDAInterlessApp extends Application {
    private MySDAService service;

    public static String PROGRAMS_DIRECTORY = MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY;

    private String[] existingProgramRefs;

    private static MySDAInterlessApp instance;

    public MySDAInterlessApp() {
        instance = this;
    }


    public static MySDAInterlessApp getInstance() {
        return instance;
    }

    /**
     * DB initialised by default, just call this service
     *
     * @return
     */
    public MySDAService getService() {
        if (service == null)
            return new MySDAService(this);
        return service;
    }

    public String[] getExistingProgramRefs() {
        return existingProgramRefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * TODO initially this is meant to be run in onCreate in this class
     */
    public void initialiseAllPrograms() {
        if (StringUtils.isNotBlank(PROGRAMS_DIRECTORY)) {
            File programsFolder = new File(PROGRAMS_DIRECTORY);

            if (!programsFolder.exists()) {
                programsFolder.mkdirs();
            }
            installPrograms(programsFolder);
            // TODO can the dongo handle millions of program refs in memory
            existingProgramRefs = filterPrograms(existingProgramNames(), null, null);
        }
    }

    /**
     * @return programsFolder.list();
     */
    public String[] existingProgramNames() {
        if (StringUtils.isNotBlank(PROGRAMS_DIRECTORY)) {
            File programsFolder = new File(PROGRAMS_DIRECTORY);

            if (programsFolder.exists()) {
               return programsFolder.list();
            }
        }
        return new String[0];
    }

    public String[] getExistingProgramRefsFromPrograms(List<Program> programs, String searchText, Boolean favorited) {
        List<String> refs = new ArrayList<>();
        for(Program p: programs) {
            if(Arrays.asList(existingProgramNames()).contains(p.getName())) {
                refs.add(p.getName());
            }
        }
        return filterPrograms(refs.toArray(new String[refs.size()]), searchText, favorited);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void installPrograms(File programsFolder) {
        if (programsFolder.exists() && programsFolder.list() != null
                && Arrays.asList(programsFolder.list()).contains(MySDAInterlessConstantsAndEvaluations.PROGRAMS_CSV_FILENAME)) {
            File programsCSV = new File(programsFolder.getAbsolutePath() + File.separator + MySDAInterlessConstantsAndEvaluations.PROGRAMS_CSV_FILENAME);

            if (programsCSV.exists() && programsCSV.length() > 0) {
                //TODO reset db with programs
                // TODO support importing using other loaders besides csv
                //getService().emptyDatabase();
                List<Program> programs = getService().loadProgramsFromCSV(programsCSV);

                if (programs != null) {
                    for (Program p : programs) {
                        try {
                            getService().saveProgram(p);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                programsCSV.delete();
                //encrypt existing programs
            }
        }
    }

    /**
     * @param codes
     * @param searchText, can be null or applied one or with favorited
     * @param favorited,  can be null or applied one or with searchText
     * @return
     */
    public String[] filterPrograms(String[] codes, String searchText, Boolean favorited) {
        List<String> strs = new ArrayList<String>();
        try {
            Map<String, Program> programSet = getProgramSetByCodeFromList();

            if (codes.length > 0 && programSet != null) {
                for (String s : codes) {//TODO
                    Program p = programSet.get(s.substring(0, s.indexOf(".")));

                    if (checkIfFileNameBelongsToVideoType(s) && p != null) {
                        if (MySDAInterlessUtils.programsMatcher(p, searchText)) {
                            if (favorited == null || (favorited != null && favorited && p.isFavourited())) {
                                strs.add(s);
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            Log.e("ERROR: ", e.getLocalizedMessage());
        }
        return strs.size() > 0 ? strs.toArray(new String[strs.size()]) : new String[]{};
    }

    public Map<String, Program> getProgramSetByCodeFromList() throws SQLException {
        Map<String, Program> programSet = new HashMap<>();
        for (Program p : getService().getAllPrograms()) {
            programSet.put(p.getCode(), p);
        }

        return programSet;
    }
}
