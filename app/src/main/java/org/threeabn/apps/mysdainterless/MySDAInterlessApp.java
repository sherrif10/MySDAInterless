package org.threeabn.apps.mysdainterless;

import android.app.Application;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.api.MySDAService;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by k-joseph on 06/03/2018.
 */

public class MySDAInterlessApp extends Application {
    private MySDAService service;
    String programsFolderPath = MySDAInterlessConstantsAndEvaluations.PROGRAMS_DIRECTORY;

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

    @Override
    public void onCreate() {
        super.onCreate();

        if (StringUtils.isNotBlank(programsFolderPath)) {
            File programsFolder = new File(programsFolderPath);

            if (!programsFolder.exists()) {
                programsFolder.mkdirs();
            }
            installPrograms();
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void installPrograms() {
        if (StringUtils.isNoneBlank(programsFolderPath)) {
            File programsFolder = new File(programsFolderPath);

            if (programsFolder.list() != null && programsFolder.exists()
                    && Arrays.asList(programsFolder.list()).contains(MySDAInterlessConstantsAndEvaluations.PROGRAMS_CSV_FILENAME)) {
                File programsCSV = new File(programsFolder.getAbsolutePath() + File.separator + MySDAInterlessConstantsAndEvaluations.PROGRAMS_CSV_FILENAME);

                if (programsCSV.exists() && programsCSV.length() > 0) {
                    //TODO reset db with programs
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
                }
            }
        }
    }

}
