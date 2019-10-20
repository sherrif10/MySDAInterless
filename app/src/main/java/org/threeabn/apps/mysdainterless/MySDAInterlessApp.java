package org.threeabn.apps.mysdainterless;

import android.app.Application;
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

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * TODO initially this is meant to be run in onCreate in this class
     */
    public void initialiseAllPrograms() {
        installPrograms();
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

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Deprecated
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
                            //encrypt existing programs
                            //CryptoLauncher.encrypt(new File(programsFolder.getAbsolutePath() + File.separator + p.getName()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                programsCSV.delete();
            }
        }
    }

    private void installPrograms()  {
        if (StringUtils.isNotBlank(PROGRAMS_DIRECTORY)) {
            File programsFolder = new File(PROGRAMS_DIRECTORY);
            if (programsFolder.exists() & programsFolder.isDirectory()) {
                File install = new File(programsFolder.getAbsolutePath() + File.separator + MySDAInterlessConstantsAndEvaluations.INSTALL);
                if(install.exists() && programsFolder.listFiles() != null) {//null without permission
                    for (File categoryFolder : programsFolder.listFiles()) {
                        if(categoryFolder.isDirectory() && Arrays.asList(categoryFolder.list()).contains(MySDAInterlessConstantsAndEvaluations.INSTALL)) {
                            String category = categoryFolder.getName();
                            for (File programFile : categoryFolder.listFiles()) {
                                String programFileName = programFile.getName();
                                if (!programFileName.startsWith(".") && checkIfFileNameBelongsToVideoType(programFileName)) {
                                    String[] programProps = getFileNameWithOutExtension(programFile).split("-");
                                    String code = programProps.length == 1 ? programProps[0] : programProps[1];
                                    String name = programProps.length == 1 ? "" : programProps[0].replaceAll("_", " ");
                                    Program program = new Program(code, name, Program.ProgramCategory.valueOfFromDisplayName(category), programFileName);
                                    try {
                                        getService().saveProgram(program);
                                        //encrypt existing programs
                                        //CryptoLauncher.encrypt(programFile);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                    install.delete();
                }
            }
        }
    }

    private String getFileNameWithOutExtension(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos);
        }
        return name;
    }

    /**
     * @param searchText, can be null or applied one or with favorited
     * @param favorited,  can be null or applied one or with searchText
     * @return
     */
    public List<Program> filterPrograms(String searchText, Boolean favorited) {
        List<Program> programs = new ArrayList<Program>();
        try {
            for (Program p : getService().getAllPrograms()) {//TODO
                if (MySDAInterlessUtils.programsMatcher(p, searchText)) {
                    if (favorited == null || (favorited != null && favorited && p.isFavourited())) {
                        programs.add(p);
                    }
                }
            }
        } catch (Exception e) {
            Log.e("ERROR: ", e.getLocalizedMessage());
        }
        return programs;
    }
}
