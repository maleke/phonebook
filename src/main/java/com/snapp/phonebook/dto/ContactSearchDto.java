package com.snapp.phonebook.dto;

import java.io.Serializable;

public class ContactSearchDto implements Serializable {
    private String name;
    private String phoneNumber;
    private String email;
    private String organization;
    private String github;

    //region getter And Setter
    public String getName() {
        return name;
    }

    public ContactSearchDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ContactSearchDto setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ContactSearchDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public ContactSearchDto setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getGithub() {
        return github;
    }

    public ContactSearchDto setGithub(String github) {
        this.github = github;
        return this;
    }

    //endregion

    @Override
    public String toString() {
        return "ContactSearchDto{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}
