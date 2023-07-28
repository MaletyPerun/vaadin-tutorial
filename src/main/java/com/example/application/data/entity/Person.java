package com.example.application.data.entity;

import com.example.application.data.util.DateTimeUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

import static com.example.application.data.util.DateTimeUtil.DATE_TIME_PATTERN;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String patronymic;
    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDate birthday;
    @Email
    private String email;
    private String password;
    @NotBlank
    @NotEmpty
    private String phone;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "person_role",
            joinColumns = @JoinColumn(name = "person_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"person_id", "role"}, name = "uk_person_role"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    public Person(long id, String firstName, String lastName, String patronymic, LocalDate birthday, String email, String phone, boolean admin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        setRoles(admin);
    }

    public void setRoles(boolean isAdmin) {
        this.roles = isAdmin ? Set.of(Role.USER, Role.ADMIN) : Set.of(Role.USER);
    }

    public Person() {
    }
}
