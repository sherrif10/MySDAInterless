package org.threeabn.apps.mysdainterless;

import android.app.Application;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.api.MySDAService;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;
import org.threeabn.apps.mysdainterless.settings.NextRun;
import org.threeabn.apps.mysdainterless.settings.OrderBy;
import org.threeabn.apps.mysdainterless.settings.Repeat;
import org.threeabn.apps.mysdainterless.settings.Settings;
import org.threeabn.apps.mysdainterless.settings.Status;
import org.threeabn.apps.mysdainterless.utils.FilesUtils;
import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType;

/**
 * Created by k-joseph on 06/03/2018.
 */

public class MySDAInterlessApp extends Application {
    private MySDAService service;

    private static MySDAInterlessApp instance;

    public MySDAInterlessApp() {
        instance = this;
    }


    public static MySDAInterlessApp getInstance() {
        return instance;
    }

    private String getRootFolder() {
        String postfix = "/.mysdainterless";
        File[] storages = ContextCompat.getExternalFilesDirs(this, null);
        if (storages.length <= 1) {// use internal storage
            return Environment.getExternalStorageDirectory().getAbsolutePath() + postfix;
        }
        String path = storages[storages.length-1].getAbsolutePath();//get last loaded drive or sdcard
        // use external sdcard
        return path.substring(0, StringUtils.ordinalIndexOf(path, "/", 3)) + postfix;
    }

    public String getProgramsDirectory() {
        return getRootFolder() + "/programs";
    }

    public String getSettingsFile() {
        File settingsLocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.mysdainterless");
        if(!settingsLocation.exists()) {
            settingsLocation.mkdir();
        }
        return settingsLocation.getAbsolutePath() + "/settings.json";
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
    public void initialiseAllPrograms() throws IOException {
        //CryptoLauncher.encrypt(new File(getProgramsDirectory().replace("/.mysdainterless/programs", "")+"/apps"));
        installPrograms();
        //CryptoLauncher.dencrypt(new File(getProgramsDirectory().replace("/.mysdainterless/programs", "")+"/apps"));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void installPrograms() throws IOException {
        MySDAService dbService = getService();
        String programsDirectory = getProgramsDirectory();
        Date now = new Date();
        List<Status> statuses = new ArrayList<>();
        if (StringUtils.isNotBlank(programsDirectory) && runInstallPrograms()) {
            File programsFolder = new File(programsDirectory);
            if (programsFolder.exists() & programsFolder.isDirectory()) {
                if (programsFolder.listFiles() != null) {//null without permission
                    for (File categoryFolder : programsFolder.listFiles()) {
                        if (categoryFolder.isDirectory()) {
                            ProgramCategory category = ProgramCategory.valueOfFromDisplayName(categoryFolder.getName());
                            int totalOfPrograms = 0;
                            for (int i = 0; i < categoryFolder.listFiles().length; i++) {
                                File programFile = categoryFolder.listFiles()[i];
                                String programFileName = programFile.getName();
                                if (!programFileName.startsWith(".") && checkIfFileNameBelongsToVideoType(programFileName)) {
                                    String[] programProps = getFileNameWithOutExtension(programFile).split("-");
                                    String code = programProps.length == 1 ? programProps[0] : programProps[1];
                                    String name = programProps.length == 1 ? "" : programProps[0].replaceAll("_", " ");
                                    Program program = new Program(code, name, category, programFileName);
                                    try {
                                        dbService.saveProgram(program);
                                        totalOfPrograms++;
                                        //encrypt existing programs
                                        //CryptoLauncher.encrypt(programFile);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            statuses.add(new Status(category, totalOfPrograms));
                        }
                    }
                    FilesUtils.write(getSettingsFile(), SettingsUtils.toJSONString(new Settings("1.0", now, NextRun.UPGRADE, statuses, Repeat.REPEAT_ONE, OrderBy.ORDER_BY_RANDOM, now, 15)));
                }
            }
        }
    }

    private boolean runInstallPrograms() throws IOException {
        File settingsFile = new File(getSettingsFile());
        return !settingsFile.exists() || settingsFile.length() == 0 || NextRun.INSTALL.equals(SettingsUtils.fromJSONString(FilesUtils.read(getSettingsFile())).getNextRun());
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
