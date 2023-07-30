package com.example.application.data.repository;

import com.example.application.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    // две настройки на поиск и выборку в БД


    @Query("select p from Person p " +
            "where lower(p.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(p.lastName) like lower(concat('%', :searchTerm, '%'))" +
            "or lower(p.patronymic) like lower(concat('%', :searchTerm, '%'))")
    List<Person> search(@Param("searchTerm") String searchTerm);

    @Query("select p from Person p where p.email = lower(:email)")
    Optional<Person> findByEmailIgnoreCase(String email);
}
