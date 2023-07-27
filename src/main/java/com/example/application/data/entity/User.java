package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
//@Getter
//@Setter
//@Data
//@Builder
//@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;

    @Version
    private int version;
    //    @NotBlank
    @NotEmpty
    private String firstName = "";
    //    @NotBlank
    @NotEmpty
    private String lastName = "";
    //    @NotBlank
    @NotEmpty
    private String patronymic = "";
    @NotEmpty
    private String birthday = "";
    @Email
//    @NotBlank
    @NotEmpty
    private String email = "";
    //    @NotBlank
    @NotEmpty
    private String password = "";
    //    @NotBlank
    @NotEmpty
    private String phone = "";
    @NotEmpty
    private String roles = "";

    public User() {

    }

    public User(long id, String firstName, String lastName, String patronymic, String birthday, String email, String phone, String role) {
//        super();
//        this.setId(id);
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.roles = role;
    }


//    public User() {
//
//    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String role) {
        this.roles = role;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
