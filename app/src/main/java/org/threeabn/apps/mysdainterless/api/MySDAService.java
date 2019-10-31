package org.threeabn.apps.mysdainterless.api;

import android.content.Context;
import org.threeabn.apps.mysdainterless.modal.Program;
import org.threeabn.apps.mysdainterless.modal.ProgramCategory;
import org.threeabn.apps.mysdainterless.orm.DBSession;
import org.threeabn.apps.mysdainterless.settings.OrderBy;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by k-joseph on 13/10/2017.
 */
public class MySDAService {

    DBSession dbSession;

    Context context = null;

    private static volatile MySDAService mySDAService = new MySDAService();

    //private constructor.
    private MySDAService(){}

    public static MySDAService getInstance() {
        return mySDAService;
    }

    /*
     * TODO how to make the context parameter require an aurgment to release the intance!!!
     */
    public MySDAService(Context context) {
        this.context = context;
    }

    public DBSession getDbSession() {
        return DBSession.getInstance(this.context);
    }

    /**
     * @param obj
     * @return number of either created or updated rows; 1
     * @throws SQLException
     */
    public int saveProgram(Program obj) throws SQLException {
        return getDbSession().create(obj, Program.class);
    }

    public int updateProgram(Program obj) throws SQLException {
        return getDbSession().update(obj, Program.class);
    }

    public Program getProgramByFileName(String fileName) throws SQLException {
        List<Program> list = getDbSession().getByField(Program.class, "fileName", fileName);

        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    public List<Program> getAllPrograms(OrderBy orderBy) throws SQLException {
        return getDbSession().getAll(Program.class);
    }

    public List<Program> getProgramsByCategories(List<ProgramCategory> categories, OrderBy orderBy) throws SQLException {
        return getDbSession().containedIn("category", ProgramCategory.getNames(categories), Program.class);
    }

    public List<Program> getFavouritedPrograms(OrderBy orderBy) throws SQLException {
        return getDbSession().getByField(Program.class, "favourited", true);
    }

}
