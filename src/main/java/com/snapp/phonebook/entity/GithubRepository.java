package com.snapp.phonebook.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "github_repository")
public class GithubRepository implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "repository_name")
    private String repositoryName;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;

    public GithubRepository() {
    }

    //region getterAndSetter
    public Long getId() {
        return id;
    }

    public GithubRepository setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public GithubRepository setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        return this;
    }

    public Contact getContact() {
        return contact;
    }

    public GithubRepository setContact(Contact contact) {
        this.contact = contact;
        return this;
    }
    //endregion

    @Override
    public String toString() {
        return "GithubRepository{" +
                "id=" + id +
                ", repositoryName='" + repositoryName + '\'' +
                ", contact=" + contact +
                '}';
    }
}
