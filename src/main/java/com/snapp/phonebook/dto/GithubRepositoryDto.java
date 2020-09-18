package com.snapp.phonebook.dto;

import java.io.Serializable;

public class GithubRepositoryDto implements Serializable {
    String name;

    public String getName() {
        return name;
    }

    public GithubRepositoryDto setName(String name) {
        this.name = name;
        return this;
    }
}
