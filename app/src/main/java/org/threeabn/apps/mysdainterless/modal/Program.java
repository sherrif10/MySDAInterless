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

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
	A TV program is basically what it is, a program can have properties such as name, time range (use joda api???), description, video, Series, code, episode, hosts/guests and functionalities such as favorite, set alert
*/
@DatabaseTable(tableName = "program")
public class Program extends MySDAObject {

	@DatabaseField(columnName = "name")
	private String name;

	@DatabaseField(columnName = "description")
	private String description;

	@DatabaseField(columnName = "period", foreign = true, foreignAutoRefresh = true)
	private Period period;

	@DatabaseField(columnName = "code")
	private String code;

	//TODO appropriate datatype
	@DatabaseField(columnName = "episode")
	private String episode;

	//TODO appropriate datatype
	@DatabaseField(columnName = "series")
	private String series;

	@ForeignCollectionField(columnName = "host", foreignFieldName = "hosts")
	private ForeignCollection<Person> hosts;

	@ForeignCollectionField(columnName = "guests", foreignFieldName = "guests")
	private ForeignCollection<Person> guests;

	@DatabaseField(columnName = "presentation", foreign = true, foreignAutoRefresh = true)
	private Video presentation;

	public Video getPresentation() {
		return presentation;
	}

	public void setPresentation(Video presentation) {
		this.presentation = presentation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Period getPeriod() {
		return period;
	}

	public void setPeriod(Period period) {
		this.period = period;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public ForeignCollection<Person> getHosts() {
		return hosts;
	}

	public void setHosts(ForeignCollection<Person> hosts) {
		this.hosts = hosts;
	}

	public ForeignCollection<Person> getGuests() {
		return guests;
	}

	public void setGuests(ForeignCollection<Person> guests) {
		this.guests = guests;
	}
}
