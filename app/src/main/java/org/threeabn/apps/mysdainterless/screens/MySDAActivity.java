package org.threeabn.apps.mysdainterless.screens;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.R;
import org.threeabn.apps.mysdainterless.api.MySDAService;
import org.threeabn.apps.mysdainterless.modal.Playback;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;
import org.threeabn.apps.mysdainterless.settings.NextRun;
import org.threeabn.apps.mysdainterless.settings.OrderBy;
import org.threeabn.apps.mysdainterless.settings.Repeat;
import org.threeabn.apps.mysdainterless.settings.Settings;
import org.threeabn.apps.mysdainterless.settings.Status;
import org.threeabn.apps.mysdainterless.utils.FilesUtils;
import org.threeabn.apps.mysdainterless.utils.ProgramsUtils;
import org.threeabn.apps.mysdainterless.utils.SettingsUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.threeabn.apps.mysdainterless.MySDAInterlessConstantsAndEvaluations.checkIfFileNameBelongsToVideoType;

/**
 * Created by k-joseph on 10/10/2017.
 */
public class MySDAActivity extends Activity {
    private MySDAService service;
    protected DateFormat localeFormat;
    public File settingsFile;
    public Settings settings;
    protected View progressBar;
    protected MySDAActivity currentScreen;

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard(int viewId) {
        View view = findViewById(viewId);
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void runActivityByView(final View view, final Context context) {
        view.setOnClickListener((View v) -> {
            progressBar.setVisibility(View.VISIBLE);
            if (R.id.settings == v.getId()) {
                startActivity(new Intent(context, SettingsActivity.class));
            } else if (R.id.image_search == v.getId()) {
                startActivity(new Intent(context, SearchActivity.class));
            } else if (R.id.image_favorite == v.getId()) {
                startActivity(new Intent(context, FavoriteActivity.class));
            } else if (R.id.image_list == v.getId()) {
                startActivity(new Intent(context, ProgramsListActivity.class));
            } else if (R.id.programPreviewPlay == v.getId()) {
                Intent intent = new Intent(context, PlayBackActivity.class);
                intent.putExtra("program", (Playback) view.getTag());
                startActivity(intent);
            } else if (R.id.programPreviewFavorite == v.getId()) {
                Playback playBack = (Playback) view.getTag();
                File p = new File(getProgramsDirectory() + File.separator + playBack.getProgramRefs().get(playBack.getProgramRefs().keySet().toArray()[playBack.getPosition()]));
                 if (p != null && p.exists()) {
                     try {
                         String s = p.getName();
                         Program program = TextUtils.isEmpty(s) ? null : getService().getProgramByFileName(s, settings.getOrderBy());
                         if (program != null) {
                             program.setFavourited(!program.isFavourited());
                             getService().updateProgram(program);
                             Toast.makeText(MySDAActivity.this, (program.isFavourited() ? "" : "Un-") + "Favorited: " + program.getDisplayName(), Toast.LENGTH_SHORT).show();
                         }
                     } catch (Exception e) {
                         Log.e("program_favoriting_error", e.getMessage());
                     }
                 }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localeFormat = SettingsUtils.defaultDateFormat();
        currentScreen = this;
        initialiseSettings();

        List<String> allPermissions = allPermissions();
        for (int i = 0; i < allPermissions.size(); i++) {
            askForPermissions(allPermissions.get(i), i);
        }

        //Move to MySDAInterlessApp#oncreate
        try {
            initialiseAllPrograms();
        } catch (IOException | SQLException e) {
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private List<String> allPermissions() {
        return Arrays.asList(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void askForPermissions(String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(MySDAActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MySDAActivity.this, new String[]{permission}, requestCode);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        initialiseSettings();
    }

    protected String getSettingsFile() {
        File settingsLocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/.mysdainterless");
        if (!settingsLocation.exists()) {
            settingsLocation.mkdir();
        }
        return settingsLocation.getAbsolutePath() + "/settings.json";
    }

    protected void afterLayoutInitialisation() {
        progressBar = findViewById(R.id.progressBar);
    }

    protected void initialiseSettings() {
        settingsFile = new File(getSettingsFile());
        try {
            if (settingsFile != null && settingsFile.exists() && settingsFile.length() > 0) {
                settings = SettingsUtils.fromJSONString(FilesUtils.read(settingsFile.getAbsolutePath()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO initially this is meant to be run in onCreate in this class
     */
    protected void initialiseAllPrograms() throws IOException, SQLException {
        //CryptoLauncher.encrypt(new File(getProgramsDirectory().replace("/.mysdainterless/programs", "")+"/apps"));
        installPrograms();
        //CryptoLauncher.dencrypt(new File(getProgramsDirectory().replace("/.mysdainterless/programs", "")+"/apps"));
    }

    private void installPrograms() throws IOException, SQLException {
        MySDAService dbService = getService();
        String programsDirectory = getProgramsDirectory();
        Date now = new Date();
        List<Status> statuses = new ArrayList<>();
        if (StringUtils.isNotBlank(programsDirectory) && runInstallPrograms()) {
            if(settings != null && NextRun.RE_INSTALL.equals(settings.getNextRun())) {// re-install
                settingsFile.delete();
                getService().deleteAllPrograms();
            }
            File programsFolder = new File(programsDirectory);
            if (programsFolder.exists() & programsFolder.isDirectory()) {
                if (programsFolder.listFiles() != null) {// null without permission
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
                    FilesUtils.write(settingsFile.getAbsolutePath(), SettingsUtils.toJSONString(new Settings(getVersionName(), now, NextRun.UPGRADE, statuses, Repeat.REPEAT_ALL, OrderBy.ORDER_BY_RANDOM, now, 15)));
                    initialiseSettings();
                }
            }
        }
    }

    private boolean runInstallPrograms() {
        return !settingsFile.exists() || settingsFile.length() == 0 || (settings != null && NextRun.RE_INSTALL.equals(settings.getNextRun()));
    }

    private String getVersionName() {
        String name = "1.0";
        try {
            name = this.getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    protected String getFileNameWithOutExtension(File file) {
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
    // TODO re-write to rather go to the database
    protected List<Program> filterPrograms(String searchText, Boolean favorited) {
        List<Program> programs = new ArrayList<>();
        try {
            for (Program p : getService().getAllPrograms(settings.getOrderBy())) {//TODO
                if (ProgramsUtils.programsMatcher(p, searchText)) {
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

    /**
     * Returns the first location of programs on the system in precedence; external drive via usb, external sdcard, internal sdcard
     * @return
     */
    public String getProgramsDirectory() {
        String postfix = "/.mysdainterless/programs";
        File[] storages = ContextCompat.getExternalFilesDirs(this, null);
        if (storages.length <= 1) {// use internal storage
            return Environment.getExternalStorageDirectory().getAbsolutePath() + postfix;
        }
        String path = storages[storages.length-1].getAbsolutePath();//get last loaded drive or sdcard
        File programsFolder = new File(path.substring(0, StringUtils.ordinalIndexOf(path, "/", 3)) + postfix);
        if(programsFolder.exists() && programsFolder.length() > 0) {
            return programsFolder.getAbsolutePath();
        } else if(storages.length > 1) {
            path = storages[storages.length-2].getAbsolutePath();//get last loaded drive or sdcard
            programsFolder = new File(path.substring(0, StringUtils.ordinalIndexOf(path, "/", 3)) + postfix);
            if(programsFolder.exists() && programsFolder.length() > 0) {
                return programsFolder.getAbsolutePath();
            }
        }

        // use external sdcard
        return path.substring(0, StringUtils.ordinalIndexOf(path, "/", 3)) + postfix;
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

}
