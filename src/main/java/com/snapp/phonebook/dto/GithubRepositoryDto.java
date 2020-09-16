package com.snapp.phonebook.dto;

import java.io.Serializable;

public class GithubRepositoryDto implements Serializable {
    private Long id;
    String name;

    public Long getId() {
        return id;
    }

    public GithubRepositoryDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public GithubRepositoryDto setName(String name) {
        this.name = name;
        return this;
    }
}
