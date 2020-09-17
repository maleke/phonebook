package com.snapp.phonebook.entity;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "contacts")
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

    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<GithubRepository> githubRepositories = new ArrayList<>();

    public Contact() {
    }
    //region getterAndSetter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOrganization() {
        return organization;
    }

    public String getGithub() {
        return github;
    }

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

    public List<GithubRepository> getGithubRepositories() {
        return githubRepositories;
    }

    public Contact setGithubRepositories(List<GithubRepository> githubRepositories) {
        this.githubRepositories = githubRepositories;
        return this;
    }

    //endregion
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
