package com.snapp.phonebook.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "github_repository")
public class GithubRepository implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",updatable = false, nullable = false)
    private Long id;

    @Column(name = "repository_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
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

    public String getName() {
        return name;
    }

    public GithubRepository setName(String repositoryName) {
        this.name = repositoryName;
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
                ", repositoryName='" + name + '\'' +
                ", contact=" + contact +
                '}';
    }
}
