package org.threeabn.apps.mysdainterless.modal;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by k-joseph on 17/10/2017.
 * TODO ormlite @{@link com.j256.ormlite.dao.ForeignCollection} still requires a fake foreign key i hate:
 * https://stackoverflow.com/questions/10287578/create-table-with-foreign-collection-field
 */
//TODO add to  service
@DatabaseTable(tableName = "channel_program")
public class ChannelProgram {
    @DatabaseField(columnName = "channel", foreign = true, foreignAutoRefresh = true)
    private Channel channel;
    @DatabaseField(columnName = "program", foreign = true, foreignAutoRefresh = true)
    private  Program program;

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }
}
