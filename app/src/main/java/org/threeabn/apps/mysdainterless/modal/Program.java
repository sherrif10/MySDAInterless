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

import java.io.File;
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
	@DatabaseField()
	private String name = "";

	@JsonProperty("description")
	@DatabaseField()
	private String description = "";

	/**
	 * Series code, contains a code from the serie and number string; such as; WOE000001. It must match the video file name
	 * //TODO should i have this as a object with name and code?
	 * This is the program's unique identifier
	 * */
	@JsonProperty("code")
	@DatabaseField(unique = true)
	private String code;

	//TODO appropriate datatype
	@JsonProperty("episode")
	@DatabaseField()
	private String episode;

	//TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	@JsonProperty("series")
	@DatabaseField()
	private String series;

    @JsonProperty("duration")
    @DatabaseField()
    private String duration;

    @JsonProperty("participants")
    @DatabaseField()
    private String participants;

	@JsonProperty("presentation")
	@DatabaseField()
	private String presentation;

	@JsonProperty("category")
	@DatabaseField()
	private String category;

	@JsonProperty("favourited")
	@DatabaseField()
	private boolean favourited;

	/**
	 * path file location of transcript
	 */
	@JsonProperty("transcript")
	@DatabaseField()
	private String transcript;

	public Program() {
	}

	public Program(String code) {
		setCode(code);
	}

	public Program(String code, String name, String description, String duration, String participants, String video, String transcript) {
		setName(name);
        setDescription(description);
		setCode(code);
		setDuration(duration);
		setParticipants(participants);
		setPresentation(video);
		setTranscript(transcript);
	}

	public String getPresentation() {
		return presentation;
	}

	public void setPresentation(String presentation) {
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
	public void setCode(String code) {
		this.code = code;
	}

	public String getEpisode() {
		return episode;
	}

	public void setEpisode(String episode) {
		this.episode = episode;
	}

    //TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	public String getSeries() {
		return series;
	}

    //TODO appropriate datatype
    /**
     * E.g. series code e.g. WOE
     */
	public void setSeries(String series) {
		this.series = series;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(ProgramCategory category) {
		this.category = category.name();
	}

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isFavourited() {
		return favourited;
	}

	public void setFavourited(boolean favourited) {
		this.favourited = favourited;
	}

	public String getTranscript() {
		return transcript;
	}

	public void setTranscript(String transcript) {
		this.transcript = transcript;
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
