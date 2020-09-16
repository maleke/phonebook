package com.snapp.phonebook.dto;

import javax.persistence.Column;
import java.io.Serializable;

public class ContactDto implements Serializable {
    private String name;
    @Column(name = "phone_number")
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
