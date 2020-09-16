package com.snapp.phonebook.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contacts")
@Getter
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    private String organization;
    private String github;

    public Contact setName(String name) {
        this.name = name;
        return this;
    }

    public Contact setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Contact setEmail(String email) {
        this.email = email;
        return this;
    }

    public Contact setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public Contact setGithub(String github) {
        this.github = github;
        return this;
    }

    @Override
    public String toString() {
        return "Contact{" +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", organization='" + organization + '\'' +
                ", github='" + github + '\'' +
                '}';
    }
}