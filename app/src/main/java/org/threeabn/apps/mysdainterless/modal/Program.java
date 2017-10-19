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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
	A TV program is basically what it is, a program can have properties such as name, time range (use joda api???), description, video, Series, code, episode, hosts/guests and functionalities such as favorite, set alert
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = "program")
public class Program extends MySDAObject {

	@JsonProperty("name")
	@DatabaseField(columnName = "name")
	private String name;

	@JsonProperty("description")
	@DatabaseField(columnName = "description")
	private String description;

	@JsonProperty("period")
	@DatabaseField(columnName = "period", foreign = true, foreignAutoRefresh = true)
	private Period period;

	@JsonProperty("code")
	@DatabaseField(columnName = "code")
	private String code;

	//TODO appropriate datatype
	@JsonProperty("episode")
	@DatabaseField(columnName = "episode")
	private String episode;

	//TODO appropriate datatype
	@JsonProperty("series")
	@DatabaseField(columnName = "series")
	private String series;

	@JsonProperty("presentation")
	@DatabaseField(columnName = "presentation", foreign = true, foreignAutoRefresh = true)
	private Video presentation;

	public Program() {

	}

	public Program(String name, String code, Period period, Video video) {
		setName(name);
		setCode(code);
		setPeriod(period);
		setPresentation(video);
	}

	@JsonProperty("presentation")
	public Video getPresentation() {
		return presentation;
	}

	@JsonProperty("presentation")
	public void setPresentation(Video presentation) {
		this.presentation = presentation;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("period")
	public Period getPeriod() {
		return period;
	}

	@JsonProperty("period")
	public void setPeriod(Period period) {
		this.period = period;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("episode")
	public String getEpisode() {
		return episode;
	}

	@JsonProperty("episode")
	public void setEpisode(String episode) {
		this.episode = episode;
	}

	@JsonProperty("series")
	public String getSeries() {
		return series;
	}

	@JsonProperty("series")
	public void setSeries(String series) {
		this.series = series;
	}
}
