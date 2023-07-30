package com.example.application.data.util;

import com.example.application.data.dto.PersonDto;
import com.example.application.data.model.Person;
import com.example.application.data.model.Role;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PersonUtil {

    // утильный класс для перевода Entity в Dto и обратно

    public PersonDto personToDto(Person person) {
        return new PersonDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getPatronymic(),
                person.getBirthday(),
                person.getEmail(),
                person.getPhone(),
                person.getRoles().contains(Role.ADMIN)
        );
    }

    public Person dtoToPerson(PersonDto personDto) {
        return new Person(
                personDto.getId(),
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getPatronymic(),
                personDto.getBirthday(),
                personDto.getEmail(),
                personDto.getPhone(),
                personDto.isAdmin()
        );
    }
}
