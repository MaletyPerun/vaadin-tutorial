package com.example.application.data.service;

import com.example.application.data.dto.UserDto;
import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.entity.User;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.ContactRepository;
import com.example.application.data.repository.StatusRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

//    public CrmService(ContactRepository contactRepository,
//                      CompanyRepository companyRepository,
//                      StatusRepository statusRepository) {
//        this.contactRepository = contactRepository;
//        this.companyRepository = companyRepository;
//        this.statusRepository = statusRepository;
//    }

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
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

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

//    public List<Contact> findAllContacts(String stringFilter) {
//        if (stringFilter == null || stringFilter.isEmpty()) {
//            return contactRepository.findAll();
//        } else {
//            return contactRepository.search(stringFilter);
//        }
//    }

    public List<UserDto> getAllUsers(String stringFilter){

        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll().stream()
                    .map(UserUtil::userToDto)
                    .toList();
        } else {
            return userRepository.search(stringFilter).stream()
                    .map(UserUtil::userToDto)
                    .toList();
        }
    }

    public UserDto get(long id){
        return UserUtil.userToDto(userRepository.getReferenceById(id));
    }

    public void create(UserDto userDto) {
        if (userDto == null) {
//            throw new Exception();
        }
        User created = UserUtil.dtoToUser(userDto);
        userRepository.save(created);
    }

    public void update(UserDto oldUser, long id) {
        User user = userRepository.getReferenceById(id);

        if (oldUser.getFirstName() != null) {
            user.setFirstName(oldUser.getFirstName());
        }
        if (oldUser.getLastName() != null) {
            user.setLastName(oldUser.getLastName());
        }
        if (oldUser.getPatronymic() != null) {
            user.setPatronymic(oldUser.getPatronymic());
        }
        if (oldUser.getBirthday() != null) {
            user.setBirthday(oldUser.getBirthday());
        }
        if (oldUser.getEmail() != null) {
            user.setEmail(oldUser.getEmail());
        }
        if (oldUser.getPhone() != null) {
            user.setPhone(oldUser.getPhone());
        }
        userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
