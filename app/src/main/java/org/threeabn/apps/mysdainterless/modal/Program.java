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
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
	A TV program is basically what it is, a program can have properties such as name, time range (use joda api???), description, video, Series, code, episode, hosts/guests and functionalities such as favorite, set alert
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
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

	/**
	 * Series code, contains a code from the serie and number string; such as; WOE000001. It must match the video file name
	 * //TODO should i have this as a object with name and code?
	 * */
	@JsonProperty("code")
	@DatabaseField(columnName = "code")
	private String code;

	//TODO appropriate datatype
	@JsonProperty("episode")
	@DatabaseField(columnName = "episode")
	private String episode;

	//TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	@JsonProperty("series")
	@DatabaseField(columnName = "series")
	private String series;

    @JsonProperty("duration")
    @DatabaseField(columnName = "duration")
    private String duration;

    @JsonProperty("participants")
    @DatabaseField(columnName = "participants")
    private String participants;

	@JsonProperty("presentation")
	@DatabaseField(columnName = "presentation", foreign = true, foreignAutoRefresh = true)
	private Video presentation;

	@JsonProperty("category")
	@DatabaseField(columnName = "category")
	private String category;

	public Program() {
	}

	public Program(String name, String description, String code, String duration, Video video) {
		setName(name);
        setDescription(description);
		setCode(code);
		setDuration(duration);
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

    /**
     * Series code, contains a code from the serie and number string; such as; WOE000001
     * //TODO should i have this as a object with name and code?
     * */
	@JsonProperty("code")
	public String getCode() {
		return code;
	}

    /**
     * Series code, contains a code from the serie and number string; such as; WOE000001
     * //TODO should i have this as a object with name and code?
     * */
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

    //TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	@JsonProperty("series")
	public String getSeries() {
		return series;
	}

    //TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	@JsonProperty("series")
	public void setSeries(String series) {
		this.series = series;
	}

	@JsonProperty("category")
	public String getCategory() {
		return category;
	}

	@JsonProperty("category")
	public void setCategory(ProgramCategory category) {
		this.category = category.name();
	}

    @JsonProperty("duration")
    public String getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(String duration) {
        this.duration = duration;
    }

    @JsonProperty("participants")
    public String getParticipants() {
        return participants;
    }

    @JsonProperty("participants")
    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public enum ProgramCategory {
		THREEABN_TODAY("3ABN Today"),
		PREACHING("Preaching"),
		HEALTH("Cooking, Exercise (Health)"),
		SABBATH_SCHOOL("Sabbath School"),
		CAMP_MEETING("Camp Meeting"),
		COUNSELLING("Counselling"),
		MUSIC("Music"),
        NEWS("3ABN News"),
		MISC("Miscellaneous");

		private String displayName;

		ProgramCategory(String displayName) {
			this.displayName = displayName;
		}

		public String displayName() {
			return displayName;
		}

		public static List<String> getNames(List<ProgramCategory> cats) {
            List<String> names = new ArrayList<String>();

            for(ProgramCategory c : (cats != null ? cats : Arrays.asList(ProgramCategory.values()))) {
                names.add(c.name());
            }
            return names;
        }
	}
}
