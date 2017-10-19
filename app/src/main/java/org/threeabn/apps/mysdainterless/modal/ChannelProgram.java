package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by k-joseph on 17/10/2017.
 * TODO ormlite @{@link com.j256.ormlite.dao.ForeignCollection} still requires a fake foreign key i hate:
 * https://stackoverflow.com/questions/10287578/create-table-with-foreign-collection-field
 */
//TODO add to  service
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = "channel_program")
public class ChannelProgram extends MySDAObject {
    @JsonProperty("channel")
    @DatabaseField(columnName = "channel", foreign = true, foreignAutoRefresh = true)
    private Channel channel;

    @JsonProperty("program")
    @DatabaseField(columnName = "program", foreign = true, foreignAutoRefresh = true)
    private  Program program;

    public ChannelProgram() {}

    public ChannelProgram(Channel channel, Program program) {
        setChannel(channel);
        setProgram(program);
    }

    @JsonProperty("channel")
    public Channel getChannel() {
        return channel;
    }

    @JsonProperty("channel")
    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @JsonProperty("program")
    public Program getProgram() {
        return program;
    }

    @JsonProperty("program")
    public void setProgram(Program program) {
        this.program = program;
    }
}
