package com.snapp.phonebook.mapper;

import com.snapp.phonebook.dto.GithubRepositoryDto;
import com.snapp.phonebook.entity.GithubRepository;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GithubRepositoryMapper {
    GithubRepository githubRepositoryDtoToGithubRepository(GithubRepositoryDto githubRepositoryDto);
    List<GithubRepository> githubRepositoryDtosToGithubRepositorys(List<GithubRepositoryDto> githubRepositoryDtos);

    GithubRepositoryDto githubRepositoryToGithubRepositoryDto(GithubRepository githubRepository);
    List<GithubRepositoryDto> githubRepositorysToGithubRepositoryDtos(List<GithubRepository> githubRepositorys);
}
