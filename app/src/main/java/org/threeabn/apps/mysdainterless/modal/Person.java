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

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * any person, such as a guest, host,
 *
 * @author k-joseph
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@DatabaseTable(tableName = "person")
public class Person extends MySDAObject {

    public Person() {
    }

    public Person(String firstName, String lastName, PersonCategory category) {
        setFirstName(firstName);
        setLastName(lastName);
        setCategory(category);
    }

    @JsonProperty("firstName")
    @DatabaseField(columnName = "first_name", canBeNull = false)
    private String firstName;

    @JsonProperty("lastName")
    @DatabaseField(columnName = "last_name", canBeNull = false)
    private String lastName;

    @JsonProperty("otherNames")
    @DatabaseField(columnName = "other_names")
    private String otherNames;

    @JsonProperty("phoneNumber")
    @DatabaseField(columnName = "phone_number")
    private String phoneNumber;

    @JsonProperty("emailAddress")
    @DatabaseField(columnName = "email_address")
    private String emailAddress;

    @JsonProperty("address")
    @DatabaseField(columnName = "address")
    private String address;

    @JsonProperty("dateOfBirth")
    @DatabaseField(columnName = "date_of_birth", dataType = DataType.DATE)
    private Date dateOfBirth;

    @JsonProperty("category")
    @DatabaseField(columnName = "category", canBeNull = false)
    private String category;

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("otherNames")
    public String getOtherNames() {
        return otherNames;
    }

    @JsonProperty("otherNames")
    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("phoneNumber")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("emailAddress")
    public String getEmailAddress() {
        return emailAddress;
    }

    @JsonProperty("emailAddress")
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplayName() {
        return (StringUtils.isNotBlank(getFirstName()) ? getFirstName() + " " : "") + (StringUtils.isNotBlank(getLastName()) ? getLastName() : "");
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(PersonCategory category) {
        this.category = category.name();
    }

    @JsonProperty("dateOfBirth")
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("dateOfBirth")
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }


    public enum PersonCategory {
        GUEST, HOST, OTHER
    }
}
