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

import org.apache.commons.lang3.StringUtils;

/**
 * any person, such as a guest, host, 
 * @author k-joseph
 *
 */
@DatabaseTable(tableName = "person")
public class Person extends MySDAObject {

	Person() {}

	Person(String firstName, String lastName) {
		setFirstName(firstName);
		setLastName(lastName);
	}

	@DatabaseField(columnName = "first_name", canBeNull = false)
	private String firstName;

	@DatabaseField(columnName = "last_name", canBeNull = false)
	private String lastName;

	@DatabaseField(columnName = "other_names")
	private String otherNames;

	@DatabaseField(columnName = "phone_number")
	private String phoneNumber;

	@DatabaseField(columnName = "email_address")
	private String emailAddress;

	@DatabaseField(columnName = "address")
	private String address;

	@DatabaseField(columnName = "category", canBeNull = false)
	private String category;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOtherNames() {
		return otherNames;
	}

	public void setOtherNames(String otherNames) {
		this.otherNames = otherNames;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDisplayName() {
		return (StringUtils.isNotBlank(getFirstName()) ? getFirstName() + " " : "") + (StringUtils.isNotBlank(getLastName()) ? getLastName() : "");
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(PersonCategory category) {
		this.category = category.name();
	}

	public enum PersonCategory {
		GUEST, HOST, OTHER
	}
}
