package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.TeamDto;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.services.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TeamMapper {
    @Lazy
    private final PersonMapper personMapper;
    @Lazy
    private final TeamService teamService;

    public TeamDto mapTeamToTeamDto(Team team) {
        return TeamDto.builder()
                .id(team.getId())
                .name(team.getName())
                .abbreviation(team.getAbbreviation())
                .teamMembersIds(mapTeamSquadToDto(team.getTeamMembers()))
                .build();
    }

    public Team mapTeamDtoToTeam(TeamDto teamDto) {
        return Team.builder()
                .name(teamDto.name())
                .abbreviation(teamDto.abbreviation())
                .teamMembers(mapTeamSquadToEntity(teamDto.teamMembersIds()))
                .build();
    }

    private Set<Long> mapTeamSquadToDto(Set<Person> squad) {
        return squad.stream()
                .map(Person::getId)
                .collect(Collectors.toSet());
    }

    private Set<Person> mapTeamSquadToEntity(Set<Long> squad) {
        return squad.stream()
                .map(personMapper::mapPersonIdToPerson)
                .collect(Collectors.toSet());
    }
}
