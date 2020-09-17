package com.snapp.phonebook.entity;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "contacts")
@Document(indexName = "contact", createIndex = false)
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(name="phone_number", type = FieldType.Keyword)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Field(type = FieldType.Keyword)
    @Email
    private String email;
    @Field(type = FieldType.Keyword)
    private String organization;
    @Field(type = FieldType.Keyword)
    private String github;

    @OneToMany(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Transient
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
