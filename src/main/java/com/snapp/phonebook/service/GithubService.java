package com.snapp.phonebook.service;

import com.snapp.phonebook.dto.GithubRepositoryDto;
import com.snapp.phonebook.entity.GithubRepository;
import com.snapp.phonebook.mapper.GithubRepositoryMapper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
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

    public Future<List<GithubRepository>> getContactGithubRepository(String userName){
        logger.info("Trying to call github webservice to get list of repositories from " + userName);
        try {
            GithubRepositoryDto[] result = restTemplate.getForObject(githubRepositoryUrl + userName + "/repos", GithubRepositoryDto[].class);
            List<GithubRepositoryDto> githubRepositoryDtos = Arrays.stream(result).collect(Collectors.toList());
            List<GithubRepository> githubRepositories = githubRepositoryMapper.githubRepositoryDtosToGithubRepositorys(githubRepositoryDtos);
            return new AsyncResult<>(githubRepositories);
        }
        catch (ResourceAccessException e) {
            if (e.getRootCause() instanceof SocketTimeoutException) {
                logger.error("SocketTimeoutException in calling github repository for userName: " + userName, e.getMessage());
            } else {
                logger.error("Unable to get the githubRepository URL for userName " + userName, e.getMessage());
            }
            return new AsyncResult<>(null);
        } catch (Exception e) {
            logger.error("Error while invoking githubRepository webservice for ." + userName);
        }
        return new AsyncResult<>(null);
    }
}
