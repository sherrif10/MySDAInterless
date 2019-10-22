package org.threeabn.apps.mysdainterless.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.threeabn.apps.mysdainterless.modal.Program;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO Hide behind {@link org.threeabn.apps.mysdainterless.api.MySDAService}
 * Created by k-joseph on 09/10/2017.
 * All foreign key on modal objects are required to be persisted first to be looked up, otherwise ormlite doesn't check these foreign existances
 */

public class DBSession extends OrmLiteSqliteOpenHelper {

    //TODO pull these 2 into the manifest file
    public static final String DB_NAME = ".mysdainterless.db";
    private static final int DB_VERSION = 2;

    //TODO TEST: if db file on device is accessible and editable, encrypt it
    public DBSession(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource cs) {
        try {
            createAllTables(cs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createAllTables(ConnectionSource cs) throws SQLException {
        Log.i(DBSession.class.getName(), "onCreate");
        /*TableUtils.createTable(cs, Period.class);
        TableUtils.createTable(cs, Video.class);
        TableUtils.createTable(cs, Transcript.class);
        TableUtils.createTable(cs, Person.class);
        TableUtils.createTable(cs, User.class);*/
        TableUtils.createTable(cs, Program.class);
        /*TableUtils.clearTable(cs, Guest.class);
        TableUtils.clearTable(cs, Host.class);
        TableUtils.createTable(cs, Channel.class);
        TableUtils.clearTable(cs, ChannelProgram.class);
        TableUtils.clearTable(cs, Favourite.class);*/
    }

    private void deleteAllTables(ConnectionSource cs) throws SQLException {
        try {
            Log.i(DBSession.class.getName(), "onCreate");
            /*TableUtils.dropTable(cs, Period.class, false);
            TableUtils.dropTable(cs, Video.class, false);
            TableUtils.dropTable(cs, Person.class, false);
            TableUtils.dropTable(cs, User.class, false);
            */
            TableUtils.dropTable(cs, Program.class, false);
            /*TableUtils.dropTable(cs, Guest.class, false);
            TableUtils.dropTable(cs, Host.class, false);
            TableUtils.dropTable(cs, Channel.class, false);
            TableUtils.dropTable(cs, ChannelProgram.class, false);
            TableUtils.dropTable(cs, Favourite.class, false);*/
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            reCreateDatabase(cs);
        }
    }

    public void reCreateDatabase(ConnectionSource cs) {
        try {
            deleteAllTables(cs);
            createAllTables(cs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getAll(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        return dao.queryForAll();
    }

    public <T> List<T> getAllOrdered(Class<T> clazz, String orderBy, boolean ascending) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        return dao.queryBuilder().orderBy(orderBy, ascending).query();
    }

    public <T> void fillObject(Class<T> clazz, T aObj) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.createOrUpdate(aObj);
    }

    public <T> void fillObjects(Class<T> clazz, Collection<T> aObjList) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        for (T obj : aObjList) {
            dao.createOrUpdate(obj);
        }
    }

    public <T> T getById(Class<T> clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.queryForId(aId);
    }

    public <T> List<T> getByField(Class<T> clazz, String fieldName, Object fieldValue) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.queryForEq(fieldName, fieldValue);
    }

    public <T> List<T> query(Class<T> clazz, Map<String, Object> aMap) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.queryForFieldValues(aMap);
    }

    public <T> List<T> queryNot(Class<T> clazz, String columnName, int value) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.queryBuilder().where().ne(columnName, value).query();
    }

    public <T> T queryFirst(Class<T> clazz, Map<String, Object> aMap) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        List<T> list = dao.queryForFieldValues(aMap);
        if (list.size() > 0)
            return list.get(0);
        else
            return null;
    }

    public <T> Dao.CreateOrUpdateStatus createOrUpdate(T obj, Class<T> objClass) throws SQLException {
        Dao<T, ?> dao = getDao(objClass);
        return dao.createOrUpdate(obj);
    }

    /**
     * @param obj
     * @param <T>
     * @return number of created rows; 1
     * @throws SQLException
     */
    public <T> int create(T obj, Class<T> objClass) throws SQLException {
        Dao<T, ?> dao = getDao(objClass);
        return dao.create(obj);
    }

    /**
     * @param obj
     * @param <T>
     * @return number of updated rows; 1
     * @throws SQLException
     */
    public <T> int update(T obj, Class<T> objClass) throws SQLException {
        Dao<T, ?> dao = getDao(objClass);
        return dao.update(obj);
    }

    public <T> int deleteById(Class<T> clazz, Object aId) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        return dao.deleteById(aId);
    }

    public <T> int deleteObjects(Class<T> clazz, Collection<T> aObjList) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.delete(aObjList);
    }

    public <T> void deleteAll(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        dao.deleteBuilder().delete();
    }

    public static HashMap<String, Object> where(String aVar, Object aValue) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put(aVar, aValue);
        return result;
    }

    public <T> List<T> containedIn(String fieldName, List<String> comparableList, Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);

        return dao.query(dao.queryBuilder().where().in(fieldName, comparableList).prepare());
    }
}
