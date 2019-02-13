/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.threeabn.apps.mysdainterless.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by k-joseph on 24/09/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "period")
public class Period extends MySDAObject {

    public Period() {}

    public Period(Date start, Date end) {
        setStart(start);
        setEnd(end);
    }

    @JsonProperty("start")
    @DatabaseField(columnName = "start_time", dataType = DataType.DATE_TIME)
    private Date start;

    @JsonProperty("end")
    @DatabaseField(columnName = "end_time", dataType = DataType.DATE_TIME)
    private Date end;

    @JsonProperty("start")
    public Date getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Date start) {
        this.start = start;
    }

    @JsonProperty("end")
    public Date getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(Date end) {
        this.end = end;
    }
}
