package com.snapp.phonebook.service;

import com.snapp.phonebook.dto.GithubRepositoryDto;
import com.snapp.phonebook.entity.GithubRepository;
import com.snapp.phonebook.mapper.GithubRepositoryMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(GithubService.class);
    private final GithubRepositoryMapper githubRepositoryMapper;
    private final RestTemplate restTemplate;

    @Value("${info.github.repository.url}")
    private String githubRepositoryUrl;

    public GithubService(GithubRepositoryMapper githubRepositoryMapper, RestTemplate restTemplate) {
        this.githubRepositoryMapper = githubRepositoryMapper;
        this.restTemplate = restTemplate;
    }

    public List<GithubRepository> getContactGithubRepository(String userName){
        GithubRepositoryDto[] result = restTemplate.getForObject(githubRepositoryUrl + userName + "/repos", GithubRepositoryDto[].class);
        List<GithubRepositoryDto> githubRepositoryDtos = Arrays.stream(result).collect(Collectors.toList());
        List<GithubRepository> githubRepositories = githubRepositoryMapper.githubRepositoryDtosToGithubRepositorys(githubRepositoryDtos);
        return githubRepositories;
    }
}
