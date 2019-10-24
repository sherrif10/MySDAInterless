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

import org.apache.commons.lang3.StringUtils;

/**
 * A TV program is basically what it is, a program can have properties such as name, time range (use joda api???), description, video, Series, code, episode, hosts/guests and functionalities such as favorite, set alert
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
     */
    @JsonProperty("code")
    @DatabaseField(unique = true, canBeNull = false)
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

    @JsonProperty("fileName")
    @DatabaseField()
    private String fileName;

    @JsonProperty("category")
    @DatabaseField()
    private String category;

    @JsonProperty("favourited")
    @DatabaseField()
    private boolean favourited = false;

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

    public Program(String code, String name, String description, String duration, String participants, String transcript, Boolean favourited, ProgramCategory category) {
        setName(name);
        setDescription(description);
        setCode(code);
        setDuration(duration);
        setParticipants(participants);
        setTranscript(transcript);
        setFavourited(favourited);
        setCategory(category);
    }

    public Program(String code, String name, ProgramCategory category, String fileName) {
        setName(name);
        setCode(code);
        setCategory(category);
        setFileName(fileName);
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
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
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * Series code, contains a code from the serie and number string; such as; WOE000001
     * //TODO should i have this as a object with name and code?
     */
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

    public void setCategory(ProgramCategory category) {
        this.category = category.name();
    }

    public ProgramCategory getCategory() {
        return ProgramCategory.valueOf(category);
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Program program = (Program) o;

        if (isFavourited() != program.isFavourited()) return false;
        if (getName() != null ? !getName().equals(program.getName()) : program.getName() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(program.getDescription()) : program.getDescription() != null)
            return false;
        if (getCode() != null ? !getCode().equals(program.getCode()) : program.getCode() != null)
            return false;
        if (getEpisode() != null ? !getEpisode().equals(program.getEpisode()) : program.getEpisode() != null)
            return false;
        if (getSeries() != null ? !getSeries().equals(program.getSeries()) : program.getSeries() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(program.getDuration()) : program.getDuration() != null)
            return false;
        if (getParticipants() != null ? !getParticipants().equals(program.getParticipants()) : program.getParticipants() != null)
            return false;
        if (getPresentation() != null ? !getPresentation().equals(program.getPresentation()) : program.getPresentation() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(program.getCategory()) : program.getCategory() != null)
            return false;
        return getTranscript() != null ? getTranscript().equals(program.getTranscript()) : program.getTranscript() == null;
    }

    @Override
    public String toString() {
        return "Program {" +
                "name='" + getName() + '\'' +
                ", code='" + getCode() + '\'' +
                ", category='" + getCategory().getDisplayName() + '\'' +
                ", favourited=" + isFavourited() +
                '}';
    }

    public String getDisplayName() {
        return (StringUtils.isBlank(getName()) ? "" : getName() + " : ") + getCode();
    }
}
