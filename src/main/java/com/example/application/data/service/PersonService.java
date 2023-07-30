package com.example.application.data.service;

import com.example.application.data.dto.PersonDto;
import com.example.application.data.model.Person;
import com.example.application.data.model.Role;
import com.example.application.data.repository.PersonRepository;
import com.example.application.data.util.PersonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;


    public List<PersonDto> getAllPersons(String stringFilter, UserDetails userDetails) {

        return userDetails.getAuthorities().contains(Role.ADMIN) ?
                getAllPersons(stringFilter) :
                getPerson(userDetails);
    }

    private List<PersonDto> getPerson(UserDetails userDetails) {
        Person person = personRepository.findByEmailIgnoreCase(userDetails.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User '" + userDetails.getUsername() + "' was not found"));

        return List.of(PersonUtil.personToDto(person));
    }

    public List<PersonDto> getAllPersons(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return personRepository.findAll().stream()
                    .map(PersonUtil::personToDto)
                    .toList();
        } else {
            return personRepository.search(stringFilter).stream()
                    .map(PersonUtil::personToDto)
                    .toList();
        }
    }

    public void savePerson(PersonDto personDto, UserDetails userDetails) {

        Person createdPerson = PersonUtil.dtoToPerson(personDto);
        if (personDto.getEmail().equals(userDetails.getUsername())) {
            Person formDB = personRepository.findByEmailIgnoreCase(personDto.getEmail()).get();
            createdPerson.setPassword(formDB.getPassword());
        }
        System.out.println(createdPerson);
        Person savePerson = personRepository.save(createdPerson);
        System.out.println(savePerson.getRoles());
    }

    public void deletePerson(long id) {
        personRepository.deleteById(id);
    }
}
