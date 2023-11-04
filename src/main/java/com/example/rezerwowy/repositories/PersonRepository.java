package com.example.rezerwowy.repositories;

import com.example.rezerwowy.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByTeamId(Long teamId);

    List<Person> findByRoleId(Long roleId);
}
