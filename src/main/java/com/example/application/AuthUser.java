package com.example.application;

import com.example.application.data.entity.Person;
import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
//@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final Person person;

    public AuthUser(@NonNull Person person) {
//        super(person.getEmail(), person.getPassword(), person.getRoles());
        super(person.getEmail(), person.getPassword(), person.getRoles());
        this.person = person;
    }

    public long id() {
        return person.getId();
    }
}