package com.example.rezerwowy.integration;

import com.example.rezerwowy.dtos.RoleDto;
import com.example.rezerwowy.models.Person;
import com.example.rezerwowy.models.Role;
import com.example.rezerwowy.models.Team;
import com.example.rezerwowy.repositories.PersonRepository;
import com.example.rezerwowy.repositories.RoleRepository;
import com.example.rezerwowy.repositories.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class RoleIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PersonRepository personRepository;

    @Test
    @DirtiesContext
    void should_createRole_when_roleDoesntExist() {
        // given
        Role role = new Role(null, "Striker", null);

        // when
        ResponseEntity<RoleDto> createResponse = restTemplate.postForEntity("/roles", role, RoleDto.class);

        // then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(roleRepository.existsById(createResponse.getBody().id())).isTrue(),
                () -> assertThat(createResponse.getBody().id()).isNotNull(),
                () -> assertThat(createResponse.getBody().name()).isEqualTo(role.getName())
        );
    }

    @Test
    @DirtiesContext
    void should_returnBadRequest_when_roleWithTheSameNameExists() {
        // given
        Role role = new Role(null, "Striker", null);
        ResponseEntity<RoleDto> createResponse = restTemplate.postForEntity("/roles", role, RoleDto.class);

        // when
        ResponseEntity<RoleDto> createResponse2 = restTemplate.postForEntity("/roles", role, RoleDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST)
        );

    }

    @Test
    @DirtiesContext
    void should_createRoleCorrectly_when_given2RolesWithTheSameId() {
        // given
        Role role = new Role(5L, "Striker", null);
        Role role2 = new Role(5L, "Defender", null);

        // when
        ResponseEntity<RoleDto> createResponse = restTemplate.postForEntity("/roles", role, RoleDto.class);
        ResponseEntity<RoleDto> createResponse2 = restTemplate.postForEntity("/roles", role2, RoleDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse2.getStatusCode()).isEqualTo(HttpStatus.CREATED),
                () -> assertThat(createResponse.getBody().id()).isNotNull(),
                () -> assertThat(createResponse2.getBody().id()).isNotNull(),
                () -> assertThat(createResponse.getBody().id()).isNotEqualTo(createResponse2.getBody().id())
        );
    }

    @Test
    @DirtiesContext
    void should_notCreateANewRole_when_nameIsNull() {
        // given
        Role role = new Role(null, null, null);

        // when
        ResponseEntity<String> createResponse = restTemplate.postForEntity("/roles", role, String.class);

        //then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DirtiesContext
    void should_returnCorrectData_when_getExistingRole() {
        //given
        Role role = Role.builder()
                .name("Striker")
                .build();
        Role savedRole = roleRepository.save(role);

        Team team = Team.builder()
                .name("FC Barcelona")
                .abbreviation("FCB")
                .build();
        Team savedTeam = teamRepository.save(team);

        Person theGOAT = Person.builder()
                .name("Lionel")
                .surname("Messi")
                .role(savedRole)
                .team(savedTeam)
                .build();
        Person suarez = Person.builder()
                .name("Luis")
                .surname("Suarez")
                .role(savedRole)
                .team(savedTeam)
                .build();
        Person savedPerson = personRepository.save(theGOAT);
        Person savedPerson2 = personRepository.save(suarez);

        //when
        ResponseEntity<RoleDto> getResponse = restTemplate.getForEntity("/roles/" + savedRole.getId(), RoleDto.class);

        //then
        Assertions.assertAll(
                () -> assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(getResponse.getBody().id()).isNotNull(),
                () -> assertThat(getResponse.getBody().name()).isEqualTo(savedRole.getName()),
                () -> assertThat(getResponse.getBody().roleOwnersIds().size()).isEqualTo(2),
                () -> assertThat(getResponse.getBody().roleOwnersIds().contains(savedPerson.getId())).isEqualTo(true),
                () -> assertThat(getResponse.getBody().roleOwnersIds().contains(savedPerson2.getId())).isEqualTo(true)
        );
    }

    @Test
    @DirtiesContext
    void should_returnNotFound_when_getRoleWithIdThatIsNotPresentInDatabase() {
        // given
        Long id = 100L;

        // when
        ResponseEntity<RoleDto> getResponse = restTemplate.getForEntity("/roles/" + id, RoleDto.class);

        // then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void should_returnNotFound_when_deleteNotExistingRole() {
        // given
        Long id = 100L;

        // when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/roles/" + id, HttpMethod.DELETE, null, Void.class);

        //then
        Assertions.assertAll(
                () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND)
        );

    }

    @Test
    @DirtiesContext
    void should_deleteInRepository_when_deleteExistingRole() {
        //given
        Role role = new Role(null, "Striker", null);
        Role savedRole = roleRepository.save(role);

        //when
        ResponseEntity<Void> deleteResponse = restTemplate.exchange("/roles/" + savedRole.getId(), HttpMethod.DELETE, null, Void.class);

        //then
        Assertions.assertAll(
                () -> assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT),
                () -> assertThat(roleRepository.existsById(savedRole.getId())).isFalse()
        );
    }
}
