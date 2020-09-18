package com.snapp.phonebook.dto;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ContactDto implements Serializable {
    @NotNull(message = "{null.name}")
    //todo:: add uniqueness
    private String name;
    private String phoneNumber;
    private String email;
    private String organization;
    private String github;

    public ContactDto() {
    }

    //region getterAndSetter
    public String getName() {
        return name;
    }

    public ContactDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ContactDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public ContactDto setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getGithub() {
        return github;
    }

    public ContactDto setGithub(String github) {
        this.github = github;
        return this;
    }
    //endregion
    @Override
    public String toString() {
        return "ContactDto{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
