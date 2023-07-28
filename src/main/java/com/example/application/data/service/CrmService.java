package com.example.application.data.service;

import com.example.application.data.dto.PersonDto;
import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import com.example.application.data.util.PersonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;

    private final PersonRepository personRepository;


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


    public void createPerson(PersonDto personDto) {
        if (personDto == null) {
            System.err.println("Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        Person createdPerson = PersonUtil.dtoToPerson(personDto);
        personRepository.save(createdPerson);
    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        // TODO: 27.07.2023 валидация входящих полей
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public void deletePerson(long id) {
        personRepository.deleteById(id);
    }
}
