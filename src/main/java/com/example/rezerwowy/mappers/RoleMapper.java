package com.example.rezerwowy.mappers;

import com.example.rezerwowy.dtos.RoleDto;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    @Lazy
    private final PersonMapper personMapper;

    public RoleDto mapRoleToRoleDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .roleOwnersIds(mapRoleOwnersToDto(role.getRoleOwners()))
                .build();
    }

    public Role mapRoleDtoToRole(RoleDto roleDto) {
        return Role.builder()
                .name(roleDto.name())
                .roleOwners(mapRoleOwnersToEntity(roleDto.roleOwnersIds()))
                .build();
    }

    private Set<Long> mapRoleOwnersToDto(Set<Person> roleOwners) {
        return roleOwners.stream()
                .map(Person::getId)
                .collect(Collectors.toSet());
    }

    private Set<Person> mapRoleOwnersToEntity(Set<Long> roleOwners) {
        return roleOwners.stream()
                .map(personMapper::mapPersonIdToPerson)
                .collect(Collectors.toSet());
    }
}
