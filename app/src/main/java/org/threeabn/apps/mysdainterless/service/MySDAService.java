package org.threeabn.apps.mysdainterless.service;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Channel;
import org.threeabn.apps.mysdainterless.modal.Period;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.User;
import org.threeabn.apps.mysdainterless.modal.Video;
import org.threeabn.apps.mysdainterless.orm.DBSession;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by k-joseph on 13/10/2017.
 */

public class MySDAService {

    DBSession dbSession;

    Context context = null;

    /*
     * TODO how to make the context parameter require an aurgment to release the intance!!!
     */
    public MySDAService(Context context) {
        this.context = context;
    }
    /**
     * Checks if there's an active user session besides the default which is guest
     * TODO
     * @return
     */
    public boolean checkIfLoggedIn() {
        return false;
    }

    public DBSession getDbSession() {
        if(this.dbSession == null && this.context != null)
            return OpenHelperManager.getHelper(context, DBSession.class);
        else
            return this.dbSession;//TODO initialise in inbuilt context pointing to mainactivity perhaps so as this never returns null
    }

    public void createDBSchemas(SQLiteDatabase db, ConnectionSource conn) {
        getDbSession().onCreate(db, conn);
    }

    public List<Period> getAllPeriods() throws SQLException {
        return getDbSession().getAll(Period.class);
    }

    public List<Video> getAllVideos() throws SQLException {
        return getDbSession().getAll(Video.class);
    }

    public List<User> getAllUsers() throws SQLException {
        return getDbSession().getAll(User.class);
    }

    public List<Program> getAllPrograms() throws SQLException {
        return getDbSession().getAll(Program.class);
    }

    public List<Channel> getAllChannels() throws SQLException {
        return getDbSession().getAll(Channel.class);
    }

    public Period getPeriodById(Integer id) throws SQLException {
        return getDbSession().getById(Period.class, id);
    }

    public Video getVideoById(Integer id) throws SQLException {
        return getDbSession().getById(Video.class, id);
    }

    public User getUserById(Integer id) throws SQLException {
        return getDbSession().getById(User.class, id);
    }

    public Program getProgramById(Integer id) throws SQLException {
        return getDbSession().getById(Program.class, id);
    }

    public Channel getChannelById(Integer id) throws SQLException {
        return getDbSession().getById(Channel.class, id);
    }

    public void deletePeriod(Period obj) throws SQLException {
        getDbSession().deleteById(Period.class, obj.getId());
    }

    public void deleteVideo(Video obj) throws SQLException {
        getDbSession().deleteById(Video.class, obj.getId());
    }

    public void deleteUser(User obj) throws SQLException {
        getDbSession().deleteById(User.class, obj.getId());
    }

    public void deleteProgram(Program obj) throws SQLException {
        getDbSession().deleteById(Program.class, obj.getId());
    }

    public void deleteChannel(Channel obj) throws SQLException {
        getDbSession().deleteById(Channel.class, obj.getId());
    }

    public void deleteAllPeriods() throws SQLException {
        getDbSession().deleteAll(Period.class);
    }

    public void deleteAllVideos() throws SQLException {
        getDbSession().deleteAll(Video.class);
    }

    public void deleteAllUsers() throws SQLException {
        getDbSession().deleteAll(User.class);
    }

    public void deleteAllPrograms() throws SQLException {
        getDbSession().deleteAll(Program.class);
    }

    public void deleteAllChannels() throws SQLException {
        getDbSession().deleteAll(Channel.class);
    }

    public Dao.CreateOrUpdateStatus savePeriod(Period obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveVideo(Video obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveUser(User obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveProgram(Program obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveChannel(Channel obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Period getPeriodByUuid(String uuid) throws SQLException {
        List<Period> list = getDbSession().getByField(Period.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public Video getVideoByUuid(String uuid) throws SQLException {
        List<Video> list = getDbSession().getByField(Video.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public User getUserByUuid(String uuid) throws SQLException {
        List<User> list = getDbSession().getByField(User.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public Program getProgramByUuid(String uuid) throws SQLException {
        List<Program> list = getDbSession().getByField(Program.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public Channel getChannelByUuid(String uuid) throws SQLException {
        List<Channel> list = getDbSession().getByField(Channel.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public User authenticateUser(String username, String password) throws SQLException {
        password = new PassHashing(password).generateHash();
        if(StringUtils.isNotBlank(username)) {
            for (User u : getAllUsers()) {
                if (username.equals(u.getUsername()) && (StringUtils.isBlank(password) ? StringUtils.isBlank(u.getPassword()) : password.equals(u.getPassword()))) {
                    return u;
                }
            }
        }
        return null;
    }

}
