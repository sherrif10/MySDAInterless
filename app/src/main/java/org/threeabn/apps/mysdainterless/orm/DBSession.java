package org.threeabn.apps.mysdainterless.orm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.apache.commons.lang3.StringUtils;
import org.threeabn.apps.mysdainterless.modal.Program;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * TODO Hide behind {@link org.threeabn.apps.mysdainterless.api.MySDAService}
 * Created by k-joseph on 09/10/2017.
 * All foreign key on modal objects are required to be persisted first to be looked up, otherwise ormlite doesn't check these foreign existances
 */

public class DBSession extends OrmLiteSqliteOpenHelper {
    private static DBSession instance;

    //TODO pull these 2 into the manifest file
    public static final String DB_NAME = ".mysdainterless.db";
    private static final int DB_VERSION = 2;

    //TODO TEST: if db file on device is accessible and editable, encrypt it
    public DBSession(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //reCreateDatabaseTables
        getWritableDatabase();
    }

    public static synchronized DBSession getInstance(Context context) {
        if (instance == null) {
            synchronized (DBSession.class) {
                if (instance == null) {
                    instance = new DBSession(context);
                }
            }
        }
        return instance;
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
        TableUtils.createTable(cs, Program.class);
    }

    private void deleteAllTables(ConnectionSource cs) {
        try {
            Log.i(DBSession.class.getName(), "onCreate");
            TableUtils.dropTable(cs, Program.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            reCreateDatabaseTables(cs);
        }
    }

    public void reCreateDatabaseTables(ConnectionSource cs) {
        try {
            deleteAllTables(cs);
            createAllTables(cs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> getAll(Class<T> clazz, String orderBy) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        if(StringUtils.isBlank(orderBy)) {
            return dao.queryBuilder().orderByRaw("RANDOM()").query();
        }
        return dao.queryBuilder().orderBy(orderBy, true).query();
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

    public <T> List<T> getByField(Class<T> clazz, String fieldName, Object fieldValue, String orderBy) throws SQLException {
        Dao<T, Object> dao = getDao(clazz);
        if(StringUtils.isBlank(orderBy)) {
            return dao.query(dao.queryBuilder().orderByRaw("RANDOM()").where().eq(fieldName, fieldValue).prepare());
        }

        return dao.query(dao.queryBuilder().orderBy(orderBy, true).where().eq(fieldName, fieldValue).prepare());
    }

    public <T> List<T> containedIn(String fieldName, List<String> comparableList, Class<T> clazz, String orderBy) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        if(StringUtils.isBlank(orderBy)) {
            return dao.query(dao.queryBuilder().orderByRaw("RANDOM()").where().in(fieldName, comparableList).prepare());
        }
        return dao.query(dao.queryBuilder().orderBy(orderBy, true).where().in(fieldName, comparableList).prepare());
    }

    public <T> void deleteAll(Class<T> clazz) throws SQLException {
        Dao<T, ?> dao = getDao(clazz);
        // breaking down into chunks solves: E/SQLiteLog: (1) too many SQL variables
        int chunkSize = 100;
        Collection<List<T>> chunks = dao.queryForAll().stream()
                .collect(Collectors.groupingBy(it -> new AtomicInteger().getAndIncrement() / chunkSize))
                .values();
        for(List<T> chunk : chunks) {
            dao.delete(chunk);
        }
    }
}
