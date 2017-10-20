package org.threeabn.apps.mysdainterless.api;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Channel;
import org.threeabn.apps.mysdainterless.modal.ChannelProgram;
import org.threeabn.apps.mysdainterless.modal.Favourite;
import org.threeabn.apps.mysdainterless.modal.Guest;
import org.threeabn.apps.mysdainterless.modal.Host;
import org.threeabn.apps.mysdainterless.modal.Period;
import org.threeabn.apps.mysdainterless.modal.Person;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.User;
import org.threeabn.apps.mysdainterless.modal.Video;
import org.threeabn.apps.mysdainterless.orm.DBSession;
import org.threeabn.apps.mysdainterless.security.PassHashing;

import java.io.IOException;
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

    public List<Period> getAllPeriods() throws SQLException {
        return getDbSession().getAll(Period.class);
    }

    public List<Video> getAllVideos() throws SQLException {
        return getDbSession().getAll(Video.class);
    }

    public List<Person> getAllPeople() throws SQLException {
        return getDbSession().getAll(Person.class);
    }

    public List<User> getAllUsers() throws SQLException {
        return getDbSession().getAll(User.class);
    }

    public List<Program> getAllPrograms() throws SQLException {
        return getDbSession().getAll(Program.class);
    }

    public List<Guest> getAllGuests() throws SQLException {
        return getDbSession().getAll(Guest.class);
    }

    public List<Host> getAllHosts() throws SQLException {
        return getDbSession().getAll(Host.class);
    }

    public List<ChannelProgram> getAllChannelPrograms() throws SQLException {
        return getDbSession().getAll(ChannelProgram.class);
    }

    public List<Channel> getAllChannels() throws SQLException {
        return getDbSession().getAll(Channel.class);
    }

    public List<Favourite> getAllFavourites() throws SQLException {
        return getDbSession().getAll(Favourite.class);
    }


    public Period getPeriodById(Integer id) throws SQLException {
        return getDbSession().getById(Period.class, id);
    }

    public Video getVideoById(Integer id) throws SQLException {
        return getDbSession().getById(Video.class, id);
    }

    public Person getPersonById(Integer id) throws SQLException {
        return getDbSession().getById(Person.class, id);
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

    public Guest getGuestById(Integer id) throws SQLException {
        return getDbSession().getById(Guest.class, id);
    }

    public Host getHostById(Integer id) throws SQLException {
        return getDbSession().getById(Host.class, id);
    }

    public ChannelProgram getChannelProgramById(Integer id) throws SQLException {
        return getDbSession().getById(ChannelProgram.class, id);
    }

    public Favourite getFavouriteById(Integer id) throws SQLException {
        return getDbSession().getById(Favourite.class, id);
    }

    public void deletePeriod(Period obj) throws SQLException {
        getDbSession().deleteById(Period.class, obj.getId());
    }

    public void deletePerson(Person obj) throws SQLException {
        getDbSession().deleteById(Person.class, obj.getId());
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

    public void deleteGuest(Guest obj) throws SQLException {
        getDbSession().deleteById(Guest.class, obj.getId());
    }

    public void deleteHost(Host obj) throws SQLException {
        getDbSession().deleteById(Host.class, obj.getId());
    }

    public void deleteChannelProgram(ChannelProgram obj) throws SQLException {
        getDbSession().deleteById(ChannelProgram.class, obj.getId());
    }

    public void deleteFavourite(Favourite obj) throws SQLException {
        getDbSession().deleteById(Favourite.class, obj.getId());
    }

    public void deleteAllPeriods() throws SQLException {
        getDbSession().deleteAll(Period.class);
    }

    public void deleteAllPeople() throws SQLException {
        getDbSession().deleteAll(Person.class);
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

    public void deleteAllGuests() throws SQLException {
        getDbSession().deleteAll(Guest.class);
    }

    public void deleteAllHosts() throws SQLException {
        getDbSession().deleteAll(Host.class);
    }

    public void deleteAllChannelPrograms() throws SQLException {
        getDbSession().deleteAll(ChannelProgram.class);
    }

    public void deleteAllFavourites() throws SQLException {
        getDbSession().deleteAll(Favourite.class);
    }

    public Dao.CreateOrUpdateStatus savePeriod(Period obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveVideo(Video obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus savePerson(Person obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveUser(User obj) throws SQLException {
        //TODO looks like setting unique = true on this user field didn't work
        if(getUserByUsername(obj.getUsername()) == null)
            return getDbSession().createOrUpdate(obj);
        return new Dao.CreateOrUpdateStatus(false,  false, 0);
    }

    public Dao.CreateOrUpdateStatus saveProgram(Program obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveChannel(Channel obj) throws SQLException {
        return getDbSession().createOrUpdate(obj);
    }

    public Dao.CreateOrUpdateStatus saveFavourite(Favourite obj) throws SQLException {
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

    public Person getPersonByUuid(String uuid) throws SQLException {
        List<Person> list = getDbSession().getByField(Person.class, "uuid", uuid);

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

    public User getUserByUsername(String username) throws SQLException {
        List<User> list = getDbSession().getByField(User.class, "username", username);

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

    public Program getProgramByCode(String code) throws SQLException {
        List<Program> list = getDbSession().getByField(Program.class, "code", code);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public List<Program> getProgramsBySeriesCode(String seriesCode) throws SQLException {
        return getDbSession().getByField(Program.class, "series", seriesCode);
    }

    public Favourite getFavouriteByUuid(String uuid) throws SQLException {
        List<Favourite> list = getDbSession().getByField(Favourite.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public List<Program> getProgramsBySeries(String seriesCode) throws SQLException {
        return getDbSession().getByField(Program.class, "series", seriesCode);
    }

    public List<Program> getProgramsByCategories(List<Program.ProgramCategory> categories) throws SQLException {
        getDbSession().containedIn("category", Program.ProgramCategory.getNames(categories), Program.class);

        return null;
    }

    //TODO in the future getPrograms that fall in a duration

    public Channel getChannelByUuid(String uuid) throws SQLException {
        List<Channel> list = getDbSession().getByField(Channel.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public Guest getGuestByUuid(String uuid) throws SQLException {
        List<Guest> list = getDbSession().getByField(Guest.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public Host getHostByUuid(String uuid) throws SQLException {
        List<Host> list = getDbSession().getByField(Host.class, "uuid", uuid);

        if(list != null && list.size() == 1)
            return list.get(0);
        return null;
    }

    public ChannelProgram getChannelProgramByUuid(String uuid) throws SQLException {
        List<ChannelProgram> list = getDbSession().getByField(ChannelProgram.class, "uuid", uuid);

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

    //TODO fix this to return the logged in user, kind of the app needs to keep an active user session for n time and close it automatically if no active app usage is ongoing
    public User getAnthenticatedUser() {
        return null;
    }

    /**
     * NEVER INVOKE SAVE IN unit test environments
     */
    public void emptyDatabase() {
        try {
            deleteAllPeriods();
            deleteAllVideos();
            deleteAllPeople();
            deleteAllUsers();
            deleteAllPrograms();
            deleteAllGuests();
            deleteAllHosts();
            deleteAllChannels();
            deleteAllChannelPrograms();
            deleteAllFavourites();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object convertJsonStringToObject(String string, Class clazz) {
        try {
            return new ObjectMapper().readValue(string, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertObjectToJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO add getallfavouritesbyUser
}
