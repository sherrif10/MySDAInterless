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

/**
	A TV Video is basically what it is, it can have properties such as name, file location/path, resume (last played time and time where it stopped)
*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "video")
public class Video extends MySDAObject {

	@JsonProperty("name")
	@DatabaseField(columnName = "name")
	private String name;

	@JsonProperty("path")
	@DatabaseField(columnName = "path", canBeNull = false)
	private String path;//TODO crate Uri object from path

	/**
	 * Time where last play ended in micro seconds within the video, TODO is meant to support resume play
	 */
	@JsonProperty("microSecs")
	@DatabaseField(columnName = "micro_secs", dataType = DataType.LONG)
	private long microSecs;

	public Video() {}

	public Video(String path) {
		setPath(path);
	}

	public Video(String name, String path) {
		setName(name);
		setPath(path);
	}

	//TODO add video quality property such as High, Mid or low

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("path")
	public String getPath() {
		return path;
	}

	/**
	 *
	 * @param path, res is consired root, so, a folder in res containing videos would have a path such as; /programs
	 */
	@JsonProperty("path")
	public void setPath(String path) {
		/*if(StringUtils.isNotBlank(path))
			path = "android.resource://mysdainterless" + path;*/
		this.path = path;
	}

	@JsonProperty("microSecs")
	public Long getMicroSecsSecs() {
		return microSecs;
	}

	@JsonProperty("microSecs")
	public void setMicroSecsSecs(long mSecs) {
		this.microSecs = mSecs;
	}
}
