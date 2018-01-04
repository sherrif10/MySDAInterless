package org.threeabn.apps.mysdainterless.api;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.threeabn.apps.mysdainterless.modal.Period;
import org.threeabn.apps.mysdainterless.modal.Person;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.User;
import org.threeabn.apps.mysdainterless.modal.Video;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by k-joseph on 13/10/2017.
 */
//TODO include test data in xml format
@RunWith(AndroidJUnit4.class)
public class MySDAServiceTest {
    MySDAService service;
    Person kjos;
    User kjose;
    Program program1;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();


    //@Ignore
    //@Before
    public void init() {
        service = new MySDAService(InstrumentationRegistry.getTargetContext());

        kjos = new Person("Joseph", "Kaweesi", Person.PersonCategory.GUEST);
        kjose = new User("k-joseph", "test123", User.UserCategory.VIEWER, kjos);

        //TODO start
        program1 = new Program("LittleRichardPromo", "3ABN Today - LittleRichard-Promo", null, "1:29:21", null, "/sdcard/MysDAInterless/.programs/LittleRichardPromo.mp4", null);

        //TODO recreate test DB rather perhaps, making use of DBSession#reCreateDatabase
        service.emptyDatabase();
    }

    @Ignore
    public void useAppContext() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Assert.assertEquals("org.threeabn.apps.mysdainterless", appContext.getPackageName());
    }

    //TODO is the db state preserved on the device
    @Ignore
    public void test_dbSetup() {
        try {
            Calendar now = Calendar.getInstance();
            Period p = new Period(now.getTime(), now.getTime());

            Assert.assertNotNull(service.getAllPeriods());
            Assert.assertEquals(0, service.getAllPeriods().size());

            service.savePeriod(p);

            Assert.assertEquals(1, service.getAllPeriods().size());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getStart());
            Assert.assertEquals(now.getTime(), ((Period) service.getAllPeriods().get(0)).getEnd());
            Assert.assertEquals(p, service.getPeriodById(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    public void test_login() {
        try {
            service.savePerson(kjos);
            Assert.assertFalse(service.checkIfLoggedIn());

            service.saveUser(kjose);

            Assert.assertNotNull(service.getPersonByUuid(kjos.getUuid()));

            User authenticatedUser = service.authenticateUser("k-joseph", "test123");

            Assert.assertNull(service.authenticateUser("k-joseph", "test"));
            Assert.assertNotNull(authenticatedUser);
            Assert.assertEquals(kjose.getUsername(), authenticatedUser.getUsername());
            Assert.assertEquals(kjose.getPassword(), authenticatedUser.getPassword());
            Assert.assertEquals(kjose.getUuid(), authenticatedUser.getUuid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_jackson_convertObjectToJsonString_and_convertJsonStringToObject() {
        String s = service.convertObjectToJsonString(kjos);

        Assert.assertTrue(s.contains("\"firstName\":\"Joseph\""));
        Assert.assertTrue(s.contains("\"lastName\":\"Kaweesi\""));
        Assert.assertEquals(((Person) service.convertJsonStringToObject(s, Person.class)).getUuid(), kjos.getUuid());
    }

    //TODO test service.checkURIResource for video play
    @Ignore
    public void test_getProgramsByCategoriesAndSeries() {
        try {
            Assert.assertEquals(0, service.getAllPrograms().size());
            Assert.assertEquals(0, service.getProgramsByCategories(null).size());

            service.saveVideo(new Video(program1.getPresentation()));
            service.saveProgram(program1);

            Program p = service.getProgramByUuid(program1.getUuid());
            Video pv = service.getVideoByUuid(new Video(p.getPresentation()).getUuid());

            Assert.assertEquals(1, service.getAllPrograms().size());
            Assert.assertNotNull(p);
            Assert.assertNotNull(pv);
            Assert.assertEquals(0, service.getProgramsByCategories(null).size());
            Assert.assertEquals(0, service.getProgramsBySeries("3ABN").size());
            Assert.assertEquals(p.getCode(), "LittleRichardPromo");
            Assert.assertNull(p.getDescription());
            Assert.assertEquals("/sdcard/MysDAInterless/.programs/LittleRichardPromo.mp4", pv.getPath());

            program1.setSeries("3ABN");
            program1.setCategory(Program.ProgramCategory.THREEABN_TODAY);

            //second saving should just actually edit existing entry
            service.saveProgram(program1);

            Assert.assertEquals(1, service.getAllPrograms().size());
            Assert.assertEquals(1, service.getProgramsByCategories(null).size());
            Assert.assertEquals(Program.ProgramCategory.THREEABN_TODAY.name(), service.getProgramsByCategories(null).get(0).getCategory());
            Assert.assertEquals(1, service.getProgramsBySeries("3ABN").size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_csvToPrograms() throws IOException {
        temporaryFolder.create();
        service = new MySDAService(InstrumentationRegistry.getTargetContext());

        File programsFile = temporaryFolder.newFile("programs.csv");
        FileWriter fileWriter = new FileWriter(programsFile);
        BufferedWriter bw = null;
        FileWriter fw = null;
        StringBuilder pgs = new StringBuilder();

        pgs.append("code,name,description,duration,participants,video,transcript\n");
        pgs.append("10HC000003,The Red Heifer,,0:58:45,John Dinzey,,\n");
        pgs.append("10HC000006,The Red Heifer,,0:58:00,\"John Lomacang (Host), Shelley Quinn\",,\n");
        pgs.append("10HC000004,The Latter Rain,,0:58:00,John Lomacang,,");
        fw = new FileWriter(programsFile.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);

        bw.write(pgs.toString());
        bw.close();
        fw.close();


        List<Program> programs = service.loadProgramsFromCSV(programsFile);

        Assert.assertNotNull(programs);
        Assert.assertEquals(3, programs.size());
        Assert.assertNull(programs.get(2).getTranscript());
        Assert.assertNull(programs.get(2).getPresentation());
        Assert.assertEquals("\"John Lomacang (Host), Shelley Quinn\"", programs.get(1).getParticipants());

        temporaryFolder.delete();
    }
}
