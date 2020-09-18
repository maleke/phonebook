package com.snapp.phonebook.service;

import com.snapp.phonebook.entity.GithubRepository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GithubService.class);
    private final RepositoryService repositoryService = new RepositoryService();

    public List<GithubRepository> getContactGithubRepository(String userName) throws IOException {
        logger.info("Trying to call github webservice to get list of repositories from " + userName);
        return repositoryService.getRepositories(userName).stream()
                .map(repo -> new GithubRepository().setName(repo.getName()))
                .collect(Collectors.toList());

    }
}
